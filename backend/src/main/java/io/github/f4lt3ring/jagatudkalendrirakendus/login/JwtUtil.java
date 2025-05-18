package io.github.f4lt3ring.jagatudkalendrirakendus.login;

import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUser;
import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private final String JWT_SECRET = "totallySecretAndNotHardcodedInAPublicRepo";
    private final long EXPIRATION_MILLIS = 20 * 1000;

    private final SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());


    public String generateToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Optional<AppUser> extractUserIfValid(AppUserRepository userRepo, String token) {
        try {
            System.out.println("jeea ");
            String idString = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            System.out.println("jee " + idString);

            Long id = Long.parseLong(idString);
            System.out.println("jee 2");
            return userRepo.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }




}
