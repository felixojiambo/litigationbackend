package com.LDLS.Litigation.Project.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCases {

    private long totalClients;
    private long activeClients;
    private long pendingClients;
    private long litigationClients;

}