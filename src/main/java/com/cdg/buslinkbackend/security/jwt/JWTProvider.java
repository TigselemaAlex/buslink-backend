package com.cdg.buslinkbackend.security.jwt;

import com.cdg.buslinkbackend.security.model.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTProvider {

    private Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expirationTimeInMs}")
    private int jwExpirationTimeInMs;

    /**
     * The function takes in an authentication object and returns a JWT token
     * 
     * @param authentication This is the authentication object that contains the
     *                       user's credentials.
     * @return A JWT token.
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwExpirationTimeInMs * 1000L * 2);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();
    }

    /**
     * The function takes a JWT token as a parameter, parses it, and returns the
     * username from the
     * token
     * 
     * @param token The JWT token that you want to parse.
     * @return The username of the user who is logged in.
     */
    public String getUsernameFromJWT(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * It takes a JWT token as a string, and returns true if the token is valid, and
     * false if it is not
     * 
     * @param authToken The token that needs to be validated.
     * @return A boolean value.
     */
    public boolean validateToken(String authToken) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
