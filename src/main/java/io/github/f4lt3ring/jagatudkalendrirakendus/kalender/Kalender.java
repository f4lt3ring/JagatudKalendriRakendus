package io.github.f4lt3ring.jagatudkalendrirakendus.kalender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;


@Entity
public class Kalender {
    @Id @GeneratedValue
    private Long id;
    private Calendar icalCalendar;

    public Kalender() {
        // Loob uue *.ics kalendri põhja
        Calendar icalCalendar = new Calendar().withProdId("-//"+id+"//iCal4j 1.0//EN")
                .withDefaults().getFluentTarget();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    /**
     * Loob uue terve päeva eventi ja lisab selle kalendrisse
     * @param eventName - Eventi nimi
     * @param eventDate - Kuupäev, kuhu event lisatakse
     * @param eventMonthNr - Kuu number, kuhu event lisatakse
     */
    public void createAllDayEvent(String eventName, int eventDate, int eventMonthNr) {
        eventMonthNr = eventMonthNr-1;
        java.util.Calendar calendarEventStart = java.util.Calendar.getInstance();
        calendarEventStart.set(java.util.Calendar.MONTH, eventMonthNr);
        calendarEventStart.set(java.util.Calendar.DAY_OF_MONTH, eventDate);

        VEvent newAllDayEvent = new VEvent(calendarEventStart.getTime().toInstant(), eventName);

        icalCalendar.add(newAllDayEvent);
    }

}
