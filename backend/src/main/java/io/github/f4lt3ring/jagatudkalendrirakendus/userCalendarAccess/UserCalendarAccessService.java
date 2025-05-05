package io.github.f4lt3ring.jagatudkalendrirakendus.userCalendarAccess;

import io.github.f4lt3ring.jagatudkalendrirakendus.kalender.Kalender;
import io.github.f4lt3ring.jagatudkalendrirakendus.kalender.KalenderRepository;
import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUser;
import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCalendarAccessService {

    private final UserCalendarAccessRepository accessRepository;
    private final AppUserRepository userRepository;
    private final KalenderRepository calendarRepository;

    @Autowired
    public UserCalendarAccessService(UserCalendarAccessRepository accessRepository, AppUserRepository userRepository, KalenderRepository calendarRepository) {
        this.accessRepository = accessRepository;
        this.userRepository = userRepository;
        this.calendarRepository = calendarRepository;
    }

    // Kontroll kas kliendil on ligipääs kalendrile
    public boolean hasAccess(Long userId, Long calendarId) {
        return accessRepository.findByUserIdAndCalendarId(userId, calendarId).isPresent();
    }

    // Uue rea loomine
    public void grantAccess(Long userId, Long calendarId) {
        if (!hasAccess(userId, calendarId)) {
            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Kalender calendar = calendarRepository.findById(calendarId)
                    .orElseThrow(() -> new IllegalArgumentException("Calendar not found"));

            UserCalendarAccess access = new UserCalendarAccess();
            access.setUser(user);
            access.setCalendar(calendar);
            accessRepository.save(access);
        }
    }

    // Rea kustutamine ehk võtame kliendil ligipääsu ära
    @Transactional
    public void revokeAccess(Long userId, Long calendarId) {
        accessRepository.deleteByUserIdAndCalendarId(userId, calendarId);
    }
}