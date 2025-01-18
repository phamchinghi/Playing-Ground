package com.pcn.playing_ground.JWT;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Autowired
    private JwtTokenUtils jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	public JwtAuthenticationFilter() {
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
            LOGGER.debug("Checking JWT authentication for request: {}", request.getRequestURI());
		} catch (Exception e) {
			LOGGER.error("Cannot set user authentication: {}", e.getMessage());
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
