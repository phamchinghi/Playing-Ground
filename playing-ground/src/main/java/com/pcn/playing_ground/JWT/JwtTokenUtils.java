package com.pcn.playing_ground.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.pcn.playing_ground.service.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);
	@Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

	@PostConstruct
	public void init() {
		System.out.println("JwtTokenUtil - jwtSecret: " + jwtSecret);
	}
    // Create token
    @SuppressWarnings("deprecation")
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256,jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtSecret).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			LOGGER.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			LOGGER.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			LOGGER.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
    }
    
    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
    	final Claims claim = getAllClaimsFromToken(token);
    	return claimsResolver.apply(claim);
    }

    public String getUsernameFromToken(String token) {
    	return getClaimsFromToken(token, Claims::getSubject);
    }
    
	public Claims getAllClaimsFromToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
			return claimsJws.getBody();

		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			LOGGER.error("JWT has expired: " + e.getMessage());
			throw e;
		} catch (io.jsonwebtoken.SignatureException e) {
			LOGGER.error("Invalid JWT signature: " + e.getMessage());
			throw e;
		} catch (io.jsonwebtoken.MalformedJwtException e) {
			LOGGER.error("Malformed JWT token: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Unexpected error while parsing JWT: " + e.getMessage());
			throw e;
		}
    }
    
    public Date getExpirationDateFromToken(String token) {
    	return getClaimsFromToken(token, Claims::getExpiration);
    }
    
    private Boolean isTokenExpired(String token) {
    	final Date expireDate = getExpirationDateFromToken(token);
    	return expireDate.before(new Date());
    }

	public String generateRefreshToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createRefreshToken(claims, userDetails.getUsername());
	}
	private String createRefreshToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 7 days
				.signWith(SignatureAlgorithm.HS256,jwtSecret)
				.compact();
	}
	public static Optional<String> getCurrentLogin(){
		SecurityContext contextHolder = SecurityContextHolder.getContext();
		return Optional.ofNullable(extractPrincipal(contextHolder.getAuthentication()));
	}

	private static String extractPrincipal(Authentication authentication) {
		if(authentication == null) return null;

		if(authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
			return userDetails.getUsername();
		}
		else if (authentication.getPrincipal() instanceof  String s) {
			return s;
		}
		return null;
	}
}
