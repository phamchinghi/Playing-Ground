package com.pcn.playing_ground.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private JwtTokenUtils jwtTokenUtil;
    private UserDetailsService userDetailsService;

	public JwtAuthenticationFilter() {
	}

	@Autowired
	public JwtAuthenticationFilter(JwtTokenUtils jwtTokenUtil, UserDetailsService userDetailsService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {			
			String jwt = getJWTFromRequest(request);
			
			if(jwt != null && jwtTokenUtil.validateToken(jwt)) {
				String userName = jwtTokenUtil.getUsernameFromToken(jwt);
				if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {					
					var userDetail = userDetailsService.loadUserByUsername(userName);
					var authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());		// from UsernamePasswordAuthenticationToken
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			
		}
		filterChain.doFilter(request, response);
	}
	
	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
		return null;
	}

}
