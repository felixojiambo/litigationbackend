package com.LDLS.Litigation.Project.Events;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@Configuration
public class EventManagementService {
    @Autowired
    private EventManagementRepo eventManagementRepo;

    public List<EventManagement> getUpcomingEvents() {
        LocalDate currentDate = LocalDate.now();
        return eventManagementRepo.findByEventDateGreaterThanEqual(currentDate);
    }


    public EntityResponse add(EventManagement eventManagement) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            String dayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMM"));
            String randomDigits = String.format("%04d", new Random().nextInt(10000));
            String eventTitle = "CAD" + "/" + dayMonth + "/" + randomDigits;
            eventManagement.setTitle(eventTitle);
            EventManagement savedEvent = eventManagementRepo.save(eventManagement);
            entityResponse.setMessage("Event added successfully");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            entityResponse.setEntity(savedEvent);
        } catch (Exception e) {
            log.error("Error occurred while adding case: {}", e.getMessage());
            entityResponse.setMessage("Failed to add case");
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return entityResponse;
    }
}
