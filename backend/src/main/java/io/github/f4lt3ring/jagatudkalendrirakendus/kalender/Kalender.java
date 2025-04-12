package io.github.f4lt3ring.jagatudkalendrirakendus.kalender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;



@Entity
public class Kalender {
    @Id @GeneratedValue
    private Long id;
    // Loob uue *.ics kalendri põhja
    private Calendar icalCalendar= new Calendar().withProdId("-//"+id+"//iCal4j 1.0//EN")
            .withDefaults().getFluentTarget();

    
    public Kalender() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    /**
     * Loob uue terve päeva eventi ja lisab selle kalendrisse
     * @param eventName - Eventi nimi
     * @param eventStart - Kuupäev, kuhu event lisatakse
     */
    public void createAllDayEvent(String eventName, LocalDateTime eventStart) {
        int eventMonthNr = eventStart.getMonthValue()-1;
        java.util.Calendar calendarEventStart = java.util.Calendar.getInstance();
        calendarEventStart.set(java.util.Calendar.MONTH, eventMonthNr);
        calendarEventStart.set(java.util.Calendar.DAY_OF_MONTH, eventStart.getDayOfMonth());

        VEvent newAllDayEvent = new VEvent(new Date(calendarEventStart.getTime()), eventName);
        Uid uid = new Uid(java.util.UUID.randomUUID().toString());
        newAllDayEvent.getProperties().add(uid);

        icalCalendar.getComponents().add(newAllDayEvent);
    }

    public void createNormalEvent(String eventName, LocalDateTime eventDate, Duration duration) {
        int eventMonthNr = eventDate.getMonthValue()-1;
        java.util.Calendar calendarEventStart = java.util.Calendar.getInstance();
        calendarEventStart.set(java.util.Calendar.YEAR, eventDate.getYear());
        calendarEventStart.set(java.util.Calendar.MONTH, eventMonthNr);
        calendarEventStart.set(java.util.Calendar.DAY_OF_MONTH, eventDate.getDayOfMonth());
        calendarEventStart.set(java.util.Calendar.HOUR_OF_DAY, eventDate.getHour());
        calendarEventStart.set(java.util.Calendar.MINUTE, eventDate.getMinute());


        VEvent newNormalEvent = new VEvent(new DateTime(calendarEventStart.getTime()),duration, eventName);
        Uid uid = new Uid(java.util.UUID.randomUUID().toString());
        newNormalEvent.getProperties().add(uid);

        icalCalendar.getComponents().add(newNormalEvent);
    }
    /**
     * Loob .ics faili mida saab kasutada kõikides seda kasutavates rakendustes
     * @param filename - faili nimi
     * @throws IOException
     */
    public void generateCalenderFile(String filename) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename + ".ics");

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icalCalendar, fout);
    }

}
