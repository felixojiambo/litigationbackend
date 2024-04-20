package com.LDLS.Litigation.Project.Dashboard;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagementRepository;
import com.LDLS.Litigation.Project.Events.EventManagement;
import com.LDLS.Litigation.Project.Events.EventManagementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    ClientManagementRepository clientManagementRepository;
    @Autowired
    private EventManagementRepo eventRepo;

    @Override
    public long countActiveCases(){
        return clientManagementRepository.count();
    }


    @Override
    public long countPendingCases() {
        return clientManagementRepository.count();
    }

    @Override
    public long countClosedCases() {
        return clientManagementRepository.count();
    }

    @Override
    public List<EventManagement> getUpcomingEvents() {

        return eventRepo.findAll();
    }
}

