package io.github.f4lt3ring.jagatudkalendrirakendus.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String signUpUser(AppUser appUser) {

        boolean emailTaken = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (emailTaken) throw new IllegalStateException("Email is already taken.");

        boolean usernameTaken = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        if (usernameTaken) throw new IllegalStateException("Username is already taken.");

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        // TODO: Send confirmation token

        return "It works";
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
