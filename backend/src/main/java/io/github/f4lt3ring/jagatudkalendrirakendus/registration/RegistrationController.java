package io.github.f4lt3ring.jagatudkalendrirakendus.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Kontroller kasutajate registreerimiseks ja e-posti kinnituslingi töötlemiseks.
 */
@RestController
@RequestMapping(path = "/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    /**
     * Loob uue kasutaja ning saadab kinnitustokeni e-postiga.
     */
    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    /**
     * Kinnitab konto valideerides tokeni.
     */
    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
