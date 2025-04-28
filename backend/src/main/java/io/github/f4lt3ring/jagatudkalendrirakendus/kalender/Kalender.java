package io.github.f4lt3ring.jagatudkalendrirakendus.kalender;

import jakarta.persistence.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Uid;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;



@Entity
@Table(name = "calendars")
public class Kalender {
    @Id @GeneratedValue
    private Long id;

    @Lob
    private String calendarData;

    private Calendar icalCalendar;


    public Kalender() {
        this.icalCalendar = new Calendar()
                .withProdId("-//Default//iCal4j 1.0//EN")
                .withDefaults()
                .getFluentTarget();
    }

    /**
     * Laeb kalendri, sõltuvalt sisendist. Kui sisendit ei tule siis loob ise uue kalendri, kuhu saab info salvestada
     */
    @PostLoad
    private void loadCalendarData() {
        if (calendarData != null && !calendarData.isEmpty()) {
            try (InputStream in = new ByteArrayInputStream(
                    calendarData.getBytes(StandardCharsets.UTF_8))) {
                CalendarBuilder builder = new CalendarBuilder();
                this.icalCalendar = builder.build(in);
            } catch (IOException | ParserException e) {
                throw new RuntimeException(
                        "Failed to parse stored calendarData for Kalender id=" + id, e);
            }
        } else {
            // Loob uue kalendri kui polnud midagi laadida
            this.icalCalendar = new Calendar()
                    .withProdId("-//" + id + "//iCal4j 1.0//EN")
                    .withDefaults()
                    .getFluentTarget();
        }
    }

    @PrePersist
    @PreUpdate
    private void saveCalendarData() {
        this.calendarData = icalCalendar.toString();
    }

    /**
     * Loob vajaliku bytearray, et oleks võimalik salvestada ja laadida kalender varasema infoga.
     * @return - tulemus
     * @throws IOException - kui polnud võimalik bytearray kirjutada
     */
    public byte[] generateCalendarBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new CalendarOutputter().output(icalCalendar, bos);
        return bos.toByteArray();
    }

    /**
     * Loob uue terve päeva eventi ja lisab selle kalendrisse
     * @param eventName - Eventi nimi
     * @param eventStart - Kuupäev, kuhu event lisatakse
     */
    public Uid createAllDayEvent(String eventName, LocalDateTime eventStart) {
        int eventMonthNr = eventStart.getMonthValue()-1;
        java.util.Calendar calendarEventStart = java.util.Calendar.getInstance();
        calendarEventStart.set(java.util.Calendar.MONTH, eventMonthNr);
        calendarEventStart.set(java.util.Calendar.DAY_OF_MONTH, eventStart.getDayOfMonth());

        VEvent newAllDayEvent = new VEvent(new Date(calendarEventStart.getTime()), eventName);
        Uid uid = new Uid(java.util.UUID.randomUUID().toString());
        newAllDayEvent.getProperties().add(uid);

        icalCalendar.getComponents().add(newAllDayEvent);
        return uid;
    }

    public Uid createNormalEvent(String eventName, LocalDateTime eventDate, Duration duration) {
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
        return uid;
    }

    public void deleteEvent(Uid uidToDelete) {
        VEvent eventToRemove = getEvent(uidToDelete);
        if (eventToRemove != null) {
            icalCalendar.getComponents().remove(eventToRemove);
            System.out.println("Event with UID " + uidToDelete + " removed.");
        } else {
            System.out.println("Event with UID " + uidToDelete + " not found.");
        }
    }

    private VEvent getEvent(Uid uidToGet) {
        for (CalendarComponent comp : icalCalendar.getComponents("VEVENT")) {
            VEvent eventToGet = (VEvent) comp;
            if (eventToGet.getUid().getValue().equals(uidToGet.getValue())) {
                return eventToGet;
            }
        }
        return null;
    }

    public void changeEvent(Uid uidToChange, LocalDateTime newEventDate, Duration duration) {

        VEvent eventToChange = getEvent(uidToChange);
        if (eventToChange != null) {
            createNormalEvent(eventToChange.getName(), newEventDate, duration);
            deleteEvent(uidToChange);
        } else {
            System.out.println("No event to change");
        }


    }

    /**
     * Loob .ics faili mida saab kasutada kõikides seda kasutavates rakendustes. Mugav .ics faili testimiseks
     * @param filename - faili nimi
     * @throws IOException
     */
    public void generateCalenderFile(String filename) throws IOException {
        FileOutputStream fout = new FileOutputStream(filename + ".ics");

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(icalCalendar, fout);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
