package com.cdg.buslinkbackend.security.jwt;

import com.cdg.buslinkbackend.security.model.UserPrincipal;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JWTProvider {
    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.expirationTimeInMs}")
    private int jwExpirationTimeInMs;


    public String generateToken(Authentication authentication) throws NoSuchAlgorithmException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwExpirationTimeInMs * 1000L * 2);
        SecretKey secretKey = secretKeyConverse(jwtSecret);
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromJWT(String token) throws NoSuchAlgorithmException {
        SecretKey secretKey = secretKeyConverse(jwtSecret);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            SecretKey secretKey = secretKeyConverse(jwtSecret);
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException |
                 NoSuchAlgorithmException e) {
            return false;
        }
    }

    private SecretKey secretKeyConverse(String jwtSecret) throws NoSuchAlgorithmException {
        return (SecretKey) KeyGenerator.getInstance(jwtSecret);
    }
}
