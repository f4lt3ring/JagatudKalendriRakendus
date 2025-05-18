package io.github.f4lt3ring.jagatudkalendrirakendus.login;

import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUser;
import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        ResponseEntity<String> invalidCredentials =
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password\n" + appUserRepository.findAll());

        Optional<AppUser> appUser = appUserRepository.findByEmail(loginDTO.getEmail());
        if (appUser.isEmpty()) return invalidCredentials;

        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), appUser.get().getPassword())) return invalidCredentials;

        String token = jwtUtil.generateToken(appUser.get().getId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token-check")
    public ResponseEntity<?> tokenCheck(@RequestBody String token) {

        Optional<AppUser> userOptional = jwtUtil.extractUserIfValid(appUserRepository, token);
        if (userOptional.isEmpty()) return ResponseEntity.ok("Invalid Token");

        AppUser user = userOptional.get();

        return ResponseEntity.ok("Token belongs to user: " + user.getUsername());
    }


}
