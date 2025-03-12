package com.pcn.playing_ground.JWT;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.pcn.playing_ground.service.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);
	@Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        LOGGER.info("JwtTokenUtil - jwtSecret: {}", jwtSecret);
	}
    // Create token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
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
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return claimsJws.getBody();
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
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
}
