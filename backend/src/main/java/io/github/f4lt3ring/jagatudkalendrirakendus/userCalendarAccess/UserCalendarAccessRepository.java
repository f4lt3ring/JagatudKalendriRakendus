package io.github.f4lt3ring.jagatudkalendrirakendus.userCalendarAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCalendarAccessRepository extends JpaRepository<UserCalendarAccess, Long> {

    Optional<UserCalendarAccess> findByUserIdAndCalendarId(Long userId, Long calendarId);

    void deleteByUserIdAndCalendarId(Long userId, Long calendarId);
}