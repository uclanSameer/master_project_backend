package com.example.neighbour.configuration.security.jwt;

import com.example.neighbour.data.User;
import com.example.neighbour.exception.ErrorResponseException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    private final String jwtSecret;
    private final int jwtExpirationMs;

    public JwtUtils(
            @Value("${jcm.jwtSecret}") String jwtSecret,
            @Value("${jcm.jwtExpirationMs}") int jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateJwtToken(User userPrincipal) {
        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getSubjectFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new ErrorResponseException(401, "Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new ErrorResponseException(401, "Invalid JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new ErrorResponseException(401, "JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new ErrorResponseException(401, "JWT claims string is empty");
        }
    }
}
