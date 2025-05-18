package io.github.f4lt3ring.jagatudkalendrirakendus.user;

import io.github.f4lt3ring.jagatudkalendrirakendus.registration.token.ConfirmationToken;
import io.github.f4lt3ring.jagatudkalendrirakendus.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
/*
 * Vastutab kasutajate registreerimise ja autentimise eest.
 */
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public String signUpUser(AppUser appUser) {

        boolean emailTaken = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (emailTaken) throw new IllegalStateException("Email is already taken.");

        boolean usernameTaken = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        if (usernameTaken) throw new IllegalStateException("Username is already taken.");

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("user with username " + username + " not found");
        }
        return appUser.get();
    }
}
