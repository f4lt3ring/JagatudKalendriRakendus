package io.github.f4lt3ring.jagatudkalendrirakendus.kalender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KalenderRepository extends JpaRepository<Kalender, Long> {

    @Query("SELECT k.id FROM Kalender k")
    List<Long> findAllIds();

}
