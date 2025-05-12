package io.github.f4lt3ring.jagatudkalendrirakendus.kalender;

import io.github.f4lt3ring.jagatudkalendrirakendus.DTO.ActionResponse;
import io.github.f4lt3ring.jagatudkalendrirakendus.DTO.EventDTO;
import io.github.f4lt3ring.jagatudkalendrirakendus.DTO.UpdateEventDTO;
import net.fortuna.ical4j.model.property.Uid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(path = "/calendar")
public class KalenderController {

    private final KalenderRepository repo;

    public KalenderController(KalenderRepository repo) {
        this.repo = repo;
    }

    @PostMapping(path = "/create")
    public ActionResponse createCalendar() {
        Kalender kal = new Kalender();
        repo.save(kal);

        ActionResponse resp = new ActionResponse();
        resp.setStatus("OK");
        resp.setMessage("Calendar created");
        resp.setUid(String.valueOf(kal.getId())); // reuse UID field to send back id
        return resp;
    }

    @PostMapping("/{id}/events")
    public ActionResponse createEvent(
            @PathVariable Long id,
            @RequestBody EventDTO dto
    ) {
        System.out.println("Trying to create event 1");
        Kalender kal = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Calendar not found"));
        System.out.println("Trying to create event 2");

        Uid uid;
        // Kontroll kas on terve päeva üritus
        if (dto.getDuration() == null) {
            uid = kal.createAllDayEvent(dto.getEventName(), dto.getEventStart());
        } else {
            uid = kal.createNormalEvent(dto.getEventName(), dto.getEventStart(), dto.getDuration());
        }
        System.out.println("Trying to create event 3");
        System.out.println(dto.getEventName() + " " + dto.getEventStart() + " " + dto.getDuration());
        System.out.println(Duration.parse("PT2H30M0S"));

        repo.save(kal);
        System.out.println("Trying to create event 4");

        ActionResponse resp = new ActionResponse();
        resp.setStatus("OK");
        resp.setUid(uid.getValue());
        resp.setMessage("Event created");
        return resp;
    }

    @PutMapping("/{id}/events/{uid}")
    public ActionResponse updateEvent(
            @PathVariable Long id,
            @PathVariable String uid,
            @RequestBody UpdateEventDTO dto
    ) throws Exception {
        Kalender kal = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Calendar not found"));

        Uid getUid = new Uid(uid);
        kal.changeEvent(getUid,
                dto.getNewEventDate(), dto.getDuration());
        repo.save(kal);

        ActionResponse resp = new ActionResponse();
        resp.setStatus("OK");
        resp.setMessage("Event updated");
        return resp;
    }

    @DeleteMapping("/{id}/events/{uid}")
    public ActionResponse deleteEvent(
            @PathVariable Long id,
            @PathVariable String uid
    ) throws Exception {
        Kalender kal = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Calendar not found"));

        kal.deleteEvent(new Uid(uid));
        repo.save(kal);

        ActionResponse resp = new ActionResponse();
        resp.setStatus("OK");
        resp.setMessage("Event deleted");
        return resp;
    }

    @GetMapping("/{id}/download")
    public byte[] downloadCalendar(@PathVariable Long id) throws IOException {
        Kalender kal = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Calendar not found"));

        return kal.generateCalendarBytes();
    }

    @GetMapping("/list-calendars")
    public List<Long> listCalendars() {
        return repo.findAllIds();
    }

}
