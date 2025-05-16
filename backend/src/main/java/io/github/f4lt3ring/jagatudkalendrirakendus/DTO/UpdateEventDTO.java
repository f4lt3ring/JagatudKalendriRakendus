package io.github.f4lt3ring.jagatudkalendrirakendus.DTO;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Sarnane EventDTOle kuid seda kasutatkse eventi muutmisel
 */
public class UpdateEventDTO {
    private LocalDateTime newEventDate;
    private Duration duration;

    // getters & setters

    public LocalDateTime getNewEventDate() {
        return newEventDate;
    }

    public void setNewEventDate(LocalDateTime newEventDate) {
        this.newEventDate = newEventDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}