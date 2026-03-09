package com.packt.cardatabase.service;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    // 1 day in ms — use much shorter (e.g. 15 min) in production
    static final long EXPIRATION_TIME = 86_400_000L;

    static final String PREFIX = "Bearer ";   // ← note the space

    // jjwt 0.12.x way to generate a key
    static final SecretKey key = Jwts.SIG.HS256.key().build();

    /** Generate a signed JWT for the given username */
    public String getToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /** Extract username from the Authorization header, or null if invalid */
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith(PREFIX)) {
            try {
                return Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token.replace(PREFIX, ""))
                        .getPayload()
                        .getSubject();
            } catch (Exception e) {
                return null;  // invalid or expired token
            }
        }
        return null;
    }
}
