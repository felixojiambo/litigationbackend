package com.emtech.Litigation.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DemandLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String loanType;
    private Long cifId;
    private Long loanAccountNumber;
    private String refNumber;
    private Date date;
    private String clientName;
    private String clientAddress;
    private String subject;
    private String contractDetails;
    private String terminationReason;
    private Date startDate;
    private Date endDate;
    private Date terminationNoticeDate;
    private String paymentAmount;
    private String paymentDeadline;
    private String paymentInstructions;
    private String lawyerName;
    private String lawyerAddress;
    private String ccRecipient;
    private boolean sent;
    // Custom getter for ccRecipient
    public String getCcRecipient() {
        return clientName;
    }
    // getter for clientName
    public String getClientName() {
        return (middleName != null && !middleName.isEmpty()) ? firstName + " " + middleName + " " + lastName : firstName + " " + lastName;
    }
    // clientAddress
    public String getClientAddress() {
        return emailAddr + ", " + postalAddress + ", " + city + ", " + postalCode + ", " + country;
    }
    //contractDetails
    public String getContractDetails() {
        return "Amount Remaining: " + amountRemaining +
                ", Loan Description: " + loanDescription +
                ", ID Number: " + idNumber +
                ", KRA PIN: " + kraPin +
                ", Loan Amount: " + loanAmount +
                ", Phone Number: " + phoneNo1 +
                ", Loan Account Number: " + loanAccNo;
    }
}

