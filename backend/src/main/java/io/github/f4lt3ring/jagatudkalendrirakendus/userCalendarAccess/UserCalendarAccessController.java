package io.github.f4lt3ring.jagatudkalendrirakendus.userCalendarAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/access")
public class UserCalendarAccessController {

    private final UserCalendarAccessService accessService;

    @Autowired
    public UserCalendarAccessController(UserCalendarAccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAccess(@RequestParam Long userId, @RequestParam Long calendarId) {
        if (accessService.hasAccess(userId, calendarId)) {
            return ResponseEntity.ok("User has access to calendar.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does NOT have access to calendar.");
        }
    }

    @PostMapping("/grant")
    public ResponseEntity<String> grantAccess(@RequestParam Long userId, @RequestParam Long calendarId) {
        accessService.grantAccess(userId, calendarId);
        return ResponseEntity.ok("Access granted.");
    }

    @PostMapping("/revoke")
    public ResponseEntity<String> revokeAccess(@RequestParam Long userId, @RequestParam Long calendarId) {
        accessService.revokeAccess(userId, calendarId);
        return ResponseEntity.ok("Access revoked.");
    }
}