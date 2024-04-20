//package com.emtech.Litigation.dtos;
//import java.util.Date;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.io.Serializable;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ClientManagementDTO implements Serializable {
//    private Long id;
//    private String caseReferenceNumber;
//    private String firstName;
//    private String middleName;
//    private String lastName;
//    private Long idNumber;
//    private String kraPin;
//    private String emailAddr;
//    private Long phoneNo1;
//    private Long phoneNo2;
//    private String postalAddress;
//    private String postalCode;
//    private String city;
//    private String country;
//    private Long loanAmount;
//    private String clientCode;
//    private Long loanAccNo;
//    private Double originalLoanAmount;
//    private String interestRate;
//    private Date startDate;
//    private Date endDate;
//    private String outPrincipal;
//    private String outInterest;
//    private Date lastAccrualDate;
//    private Date lastPaymentDate;
//    private String lastPayReceived;
//    private Date lastIntAppDate;
//    private String userAssetClass;
//    private Date classificationDate;
//    private String loanTenor;
//    private String defaultReason;
//    private Date defaultDate;
//    private String transferringOffice;
//    private Double amountRemaining;
//    private String loanDescription;
//    private String department;
//    private String officer;
//    private Date deadline;
//    private String priority;
//    private String additionalInfo;
//    private long totalClients;
//    private long activeClients;
//    private long pendingClients;
//    private long litigationClients;
//    private String status;
//    private String details;
//    private Date lastUpdated;
//    private String loanType;
//    private Long cifId;
//    private Long loanAccountNumber;
//}
package com.emtech.Litigation.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientManagementDTO implements Serializable {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long idNumber;
    private String kraPin;
    private String emailAddr;
    private Long phoneNo1;
    private Long phoneNo2;
    private String postalAddress;
    private String postalCode;
    private String city;
    private String country;
    private Long loanAmount;
    private String clientCode;
    private Long loanAccNo;
    private Double originalLoanAmount;
    private String interestRate;
    private Date startDate;
    private Date endDate;
    private String outPrincipal;
    private String outInterest;
    private Date lastAccrualDate;
    private Date lastPaymentDate;
    private String lastPayReceived;
    private Date lastIntAppDate;
    private String userAssetClass;
    private Date classificationDate;
    private String loanTenor;
    private String defaultReason;
    private Date defaultDate;
    private String transferringOffice;
    private Double amountRemaining;
    private String loanDescription;
    private String department;
    private String officer;
    private Date deadline;
    private String priority;
    private String additionalInfo;
    private long totalClients;
    private long activeClients;
    private long pendingClients;
    private long litigationClients;
    private String status;
    private String details;
    private Date lastUpdated;
}
