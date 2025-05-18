package io.github.f4lt3ring.jagatudkalendrirakendus.login.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String JWT_SECRET = "totallySecretAndNotHardcodedInAPublicRepo";
    private final long EXPIRATION_MILLIS = 60 * 60 * 1000;

    private final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());


    public String generateToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }




}
