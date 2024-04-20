package com.LDLS.Litigation.Project.ClientManagement;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ClientRequest {
    private String department;
    private String officer;
    private Date deadline;
    private String priority;
    private String additionalInfo;
}
