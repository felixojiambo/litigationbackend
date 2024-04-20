package com.LDLS.Litigation.Project.LoanManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Loans")
public class LoanManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private  String Acct_Name;
    private String  Customer_Lastname;
    private String CIF_id;
    private String Customer_email;
    private String Customer_address;
    private  String Customer_postalCode;
    private String Customer_city;
    private String Customer_country;
    private String Loan_Acc_Number;
    private Double Loan_amount;
    private Double interest;
    private Date Defaulted_date;
    private Double Remaining_Loan;
    private String Loan_Description;
    private String Status = "PENDING";
}