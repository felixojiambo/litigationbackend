package com.LDLS.Litigation.Project.Dashboard;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagementService;
import com.LDLS.Litigation.Project.Events.EventManagement;
import com.LDLS.Litigation.Project.Events.EventManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("api/v1/dashboard")
@CrossOrigin
public class DashboardController {
    @Autowired
    ClientManagementService clientManagementService;
    @Autowired
    EventManagementService eventManagementService;

//    @GetMapping("/active")
//    public List<ClientManagement> getActiveClients() {
//        return clientManagementService.getActiveClients();
//    }
//
//    @GetMapping("/litigation")
//    public List<ClientManagement> getLitigationClients() {
//        return clientManagementService.getLitigationClients();
//    }
//
//    @GetMapping("/pending")
//    public List<ClientManagement> getPendingClients() {
//        return clientManagementService.getPendingClients();
//    }
//
//    @GetMapping("/total")
//    public List<ClientManagement> getTotalClients() {
//        return clientManagementService.getTotalClients();
//    }
@GetMapping("/total-clients")
public long getTotalClientsCount() {
    return clientManagementService.countTotalClients();
}

    @GetMapping("/pending-clients")
    public long getPendingClientsCount() {
        return clientManagementService.countPendingClients();
    }

    @GetMapping("/active-clients")
    public long getActiveClientsCount() {
        return clientManagementService.countActiveClients();
    }

    @GetMapping("/litigation-clients")
    public long getLitigationClientsCount() {
        return clientManagementService.countLitigationClients();
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventManagement>> getUpcomingEvents() {
        List<EventManagement> events = eventManagementService.getUpcomingEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}