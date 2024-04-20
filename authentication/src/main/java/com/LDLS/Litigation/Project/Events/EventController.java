package com.LDLS.Litigation.Project.Events;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("api/v1/eventmanagement")
@CrossOrigin
public class EventController {
    @Autowired
    EventManagementService eventManagementService;
    @Autowired
    EventManagementRepo eventManagementRepo;
    @PostMapping("/add")
    public EntityResponse add(@RequestBody EventManagement eventManagement) {
        return eventManagementService.add(eventManagement);
    }
    @GetMapping
    public List<EventManagement> getAllEvents() {
        return eventManagementRepo.findAll();
    }
}