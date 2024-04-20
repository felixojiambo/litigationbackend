package com.LDLS.Litigation.Project.LoanManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoanManagementService {
    @Autowired
    LoanManagementRepository repository;
    public EntityResponse uploadFile(MultipartFile files) {
        EntityResponse response = new EntityResponse<>();
        try {
            List<LoanManagement> loans = readExcelFile(files);
            response.setEntity(loans);
            response.setMessage("successfully converted");
            repository.saveAll(loans);
        } catch (Exception e) {
            log.error("Failed to save loans", e);
            response.setMessage("Failed to save loans");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        }
        return response;
    }

    private List<LoanManagement> readExcelFile(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        List<LoanManagement> loans = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            LoanManagement loans1 = new LoanManagement();
            loans1.setAcct_Name(getStringValue(row.getCell(0)));
            loans1.setCustomer_address(getStringValue(row.getCell(4)));
            loans1.setCIF_id(getStringValue(row.getCell(2)));
            loans1.setLoan_amount(Double.parseDouble(getStringValue(row.getCell(9))));
            loans1.setLoan_Acc_Number(getStringValue(row.getCell(8)));
            loans1.setCustomer_city(getStringValue(row.getCell(6)));
            loans1.setCustomer_country(getStringValue(row.getCell(7)));
            loans1.setRemaining_Loan(Double.parseDouble(getStringValue(row.getCell(12))));
            loans1.setLoan_Description(getStringValue(row.getCell(13)));
            loans1.setCustomer_email(getStringValue(row.getCell(3)));
            loans1.setCustomer_Lastname(getStringValue(row.getCell(1)));
            loans1.setInterest(Double.parseDouble(getStringValue(row.getCell(10))));
            loans1.setCustomer_postalCode(getStringValue(row.getCell(5)));

            // Parse Defaulted_date
            Cell defaultedDateCell = row.getCell(11);
            if (defaultedDateCell != null) {
                Date defaultedDate = defaultedDateCell.getDateCellValue();
                loans1.setDefaulted_date(defaultedDate);
            }

            loans.add(loans1);
        }
        workbook.close();
        return loans;
    }



    private String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return null;
        }
    }

    public EntityResponse getAllLoans() {
        EntityResponse response = new EntityResponse<>();
        try {
            List<LoanManagement> bills = repository.findAll();
            if (bills.isEmpty()) {
                response.setMessage("Loans not found");
                response.setEntity(null);
                response.setStatusCode(HttpStatus.NO_CONTENT.value());
            } else {
                response.setMessage("Loans found Successfully");
                response.setEntity(bills);
                response.setStatusCode(HttpStatus.FOUND.value());
            }

        } catch (Exception e) {
            log.error("Error retrieving Loans");
            response.setMessage("Failed to retrieve Loans");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
    public EntityResponse getLoanById(Long Id) {
        EntityResponse response = new EntityResponse<>();
        try {
            Optional<LoanManagement> loan = repository.findById(Id);
            if (loan.isPresent()) {
                response.setMessage("Loan found successfully");
                response.setEntity(loan.get());
                response.setStatusCode(HttpStatus.OK.value());
            } else {
                response.setMessage("Loan not found");
                response.setEntity(null);
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.error("Error retrieving loan by ID: {}", e.getMessage());
            response.setMessage("Failed to retrieve loan");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

}

