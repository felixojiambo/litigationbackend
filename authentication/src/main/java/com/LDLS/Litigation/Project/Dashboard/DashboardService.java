package com.LDLS.Litigation.Project.Dashboard;


import com.LDLS.Litigation.Project.Events.EventManagement;

import java.util.List;

public interface DashboardService {
    long countActiveCases();
    long countPendingCases();
    long countClosedCases();
    List<EventManagement> getUpcomingEvents();
}
