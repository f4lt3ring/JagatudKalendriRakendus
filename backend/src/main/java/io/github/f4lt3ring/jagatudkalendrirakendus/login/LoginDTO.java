package io.github.f4lt3ring.jagatudkalendrirakendus.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginDTO {

    private final String email;
    private final String password;

}
