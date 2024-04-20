package com.LDLS.Litigation.Project.Events;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventManagementRepo extends JpaRepository<EventManagement, Long> {
    List<EventManagement> findByEventDateGreaterThanEqual(LocalDate eventDate);
}
