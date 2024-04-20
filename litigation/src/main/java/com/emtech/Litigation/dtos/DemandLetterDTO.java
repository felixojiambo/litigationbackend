package com.emtech.Litigation.dtos;

import java.util.Date;

public class DemandLetterDTO {
    private Long id;
    private String refNumber;
    private Date date;
    private String clientName;
    private String clientAddress;
    private String subject;
    private String contractDetails;
    private String terminationReason;
    private Date contractStartDate;
    private Date contractEndDate;
    private Date terminationNoticeDate;
    private String paymentAmount;
    private String paymentDeadline;
    private String paymentInstructions;
    private String lawyerName;
    private String lawyerAddress;
    private String ccRecipient;
}
