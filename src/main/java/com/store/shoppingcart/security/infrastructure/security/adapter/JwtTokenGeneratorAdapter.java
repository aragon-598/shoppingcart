package com.store.shoppingcart.security.infrastructure.security.adapter;

import com.store.shoppingcart.security.domain.exception.InvalidTokenException;
import com.store.shoppingcart.security.domain.model.AuthToken;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.domain.port.out.TokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenGeneratorAdapter implements TokenGenerator {
    
    private final String secretKey;
    private final long expirationMillis;
    
    public JwtTokenGeneratorAdapter(
        @Value("${jwt.secret}") String secretKey,
        @Value("${jwt.expiration}") long expirationMillis
    ) {
        this.secretKey = secretKey;
        this.expirationMillis = expirationMillis;
    }
    
    @Override
    public AuthToken generate(User user) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expirationMillis);
        
        String token = Jwts.builder()
            .setSubject(user.getId().value().toString())
            .claim("email", user.getEmail().value())
            .claim("role", user.getRole().name())
            .setIssuedAt(now)
            .setExpiration(expiresAt)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
        
        return new AuthToken(token, expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
    
    @Override
    public UserId validate(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
            
            String userIdString = claims.getSubject();
            return UserId.from(userIdString);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
