package com.service.todo.security;

import com.service.todo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String create(UserEntity userEntity) {
        Date expireDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                .signWith(secretKey)
                .setSubject(userEntity.getId())
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey).build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.getSubject();
    }
}
