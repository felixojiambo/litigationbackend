package com.emtech.Litigation.models;

import com.emtech.Litigation.enums.CaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LitigationCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String caseReferenceNumber;

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
//    @Column(nullable = false)
//    private String firstName;
//    @Column(nullable = false)
//    private String lastName;
//    private  String middleName;
//    @Column(nullable = false)
//    private String clientEmail;
//    @Column(nullable = false)
//    private String clientPhoneNumber;
//    @Column(nullable = false)
//    private String loanId;
//    @Column(nullable = false)
//    private Double loanAmount;
 private String loanType;
//    private String collateralDetails;
//    private String insuranceCoverage;
//    private String securityAttached;
//    private String documentList;
//    @Column(nullable = false)
 private Long cifId;
//    private Date loanStartDate;
//    private Date loanEndDate;
//    @Column(nullable = false)
//    private Long idNumber;
//    @Column(nullable = false)
//    private String kraPinNumber;
//    private Long alternativePhoneNumber;
//    private String postalAddress;
//    private String postalCode;
//    private String city;
//    private String country;
//    private String clientCode;
 private Long loanAccountNumber;
//    private String interestRate;
//    private Date defaultDate;
//    private Double amountRemaining;
//    private String loanDescription;
//    private Date caseCreationDate;
//    private String caseCreatedBy;
//    private String caseNotes;
//    @Enumerated(EnumType.STRING)
//    private CaseStatus caseStatus;
    @PrePersist
    public void generateCaseReferenceNumber() {
        String loanTypePrefix = loanType.substring(0, Math.min(2, loanType.length())).toUpperCase();
        String cifIdDigits = String.format("%02d", cifId % 100);
        String loanAccountDigits = String.format("%02d", loanAccountNumber % 100);

        caseReferenceNumber = loanTypePrefix + cifIdDigits + loanAccountDigits;
    }
}
