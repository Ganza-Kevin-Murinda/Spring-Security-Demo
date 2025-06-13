package com.mulk.springsecuritydemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET= "hd9YQSiqSsWVqlrGUrPHaAg1uyjHWbrv23HsDUreLjc/wxgrcRC6nEWTTCGQwAUp";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1hour

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUsernameFromToken(String token) {
        return extraClaims(token).getSubject();
    }

    private Claims extraClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {

        // check if username is same as username in userDetails and if the token that we have is not expired
       return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extraClaims(token).getExpiration().before(new Date());
    }
}
