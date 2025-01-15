package com.pcn.playing_ground.JWT;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtils {

	@Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;
    
    // Create token
    @SuppressWarnings("deprecation")
    public String generateToken(String username) {
//    	Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256,jwtSecret)
                .compact();
    }

    public boolean validateToken(String token, String username) {
    	return (username.equals(getUsernameFromToken(token)) && !isTokenExpired(token));
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
//			Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//    		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
			return claimsJws.getBody();

		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			System.err.println("JWT has expired: " + e.getMessage());
			throw e;
		} catch (io.jsonwebtoken.SignatureException e) {
			System.err.println("Invalid JWT signature: " + e.getMessage());
			throw e;
		} catch (io.jsonwebtoken.MalformedJwtException e) {
			System.err.println("Malformed JWT token: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.err.println("Unexpected error while parsing JWT: " + e.getMessage());
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


}
