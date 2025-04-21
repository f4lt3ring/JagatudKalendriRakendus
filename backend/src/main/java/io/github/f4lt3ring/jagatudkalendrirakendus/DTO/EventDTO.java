
package io.github.f4lt3ring.jagatudkalendrirakendus.DTO;

import java.time.Duration;
import java.time.LocalDateTime;

public class EventDTO {
    private String eventName;
    private LocalDateTime eventStart;
    private Duration duration;// if null → all‑day event

    // getters & setters

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}