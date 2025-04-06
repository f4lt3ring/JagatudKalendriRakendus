package io.github.f4lt3ring.jagatudkalendrirakendus.appuser;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppUserService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public String signUpUser(AppUser appUser) {
    boolean userExists = appUserRepository
        .findByEmail(appUser.getEmail())
        .isPresent();
    if (userExists) {
      throw new IllegalStateException("Email is already taken.");
    }

    String encodedPassword = bCryptPasswordEncoder
        .encode(appUser.getPassword());

    appUser.setPassword(encodedPassword);

    appUserRepository.save(appUser);

    // TODO: Send confirmation token

    return "It works";
  }
}
