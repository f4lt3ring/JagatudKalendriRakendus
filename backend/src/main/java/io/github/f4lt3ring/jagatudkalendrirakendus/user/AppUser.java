package io.github.f4lt3ring.jagatudkalendrirakendus.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
public class AppUser implements UserDetails {


    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String username;
    private String password;
    private AppUserRole appUserRole;

    public AppUser(String email, String username, String password, AppUserRole role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.appUserRole = role;
    }

    public AppUser() {
        return;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(appUserRole.name()));
    }
}
