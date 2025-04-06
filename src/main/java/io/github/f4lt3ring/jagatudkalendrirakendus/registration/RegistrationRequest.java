package io.github.f4lt3ring.jagatudkalendrirakendus.registration;

import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String email;
    private final String username;
    private final String password;
    private final AppUserRole role;
}
