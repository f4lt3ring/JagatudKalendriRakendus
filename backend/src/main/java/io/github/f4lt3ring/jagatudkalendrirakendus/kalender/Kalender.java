package io.github.f4lt3ring.jagatudkalendrirakendus.kalender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public void deleteEvent(Uid uidToDelete, String icsPath) throws ParserException, IOException {
        VEvent eventToRemove = getEvent(uidToDelete, icsPath);
        if (eventToRemove != null) {
            icalCalendar.getComponents().remove(eventToRemove);
            System.out.println("Event with UID " + uidToDelete + " removed.");
        } else {
            System.out.println("Event with UID " + uidToDelete + " not found.");
        }

    }

    private VEvent getEvent(Uid uidToGet, String icsPath) throws ParserException, IOException {
        try(InputStream fin = new FileInputStream(icsPath)) {
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(fin);

            VEvent eventToGet = null;
            for (Object component : calendar.getComponents("VEVENT")) {
                VEvent event = (VEvent) component;
                Uid uid = event.getUid();
                if (uid != null && uid.getValue().equals(uidToGet.getValue())) {
                    eventToGet = event;
                    break;
                }
            }
            return eventToGet;
        }
    }

    public void changeEvent(Uid uidToChange, String icsPath, LocalDateTime newEventDate, Duration duration) throws ParserException, IOException {

        VEvent eventToChange = getEvent(uidToChange, icsPath);
        if (eventToChange != null) {
            createNormalEvent(eventToChange.getName(), newEventDate, duration);
            deleteEvent(uidToChange, icsPath);
        } else {
            System.out.println("No event to change");
        }


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
