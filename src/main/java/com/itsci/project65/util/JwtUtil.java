package com.itsci.project65.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpiration = 86400000; // 24 hours in milliseconds

    public JwtUtil() {
        // Generate a secure key for HS256
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(String username, Integer farmerId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("farmerId", farmerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Integer extractFarmerId(String token) {
        return extractClaim(token, claims -> claims.get("farmerId", Integer.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
