package io.github.f4lt3ring.jagatudkalendrirakendus.userCalendarAccess;

import io.github.f4lt3ring.jagatudkalendrirakendus.kalender.Kalender;
import io.github.f4lt3ring.jagatudkalendrirakendus.user.AppUser;
import jakarta.persistence.*;

/**
 * Klass vahetabeli jaoks, et seostada kasutajad nende enda kalendritega. Kui tabelis leidub sissekanne kasuta ja kalendri
 * id-ga siis saame kinnitada, et kasutajal on ligipääs sellele kindlale kalendrile
 */
@Entity
@Table(name = "user_calendar_access")
public class UserCalendarAccess {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Kalender calendar;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kalender getCalendar() {
        return calendar;
    }

    public void setCalendar(Kalender calendar) {
        this.calendar = calendar;
    }
}
