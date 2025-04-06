package io.github.f4lt3ring.jagatudkalendrirakendus.registration;

import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

  public String register(RegistrationRequest request) {
    return "Registration successful";
  }
}
