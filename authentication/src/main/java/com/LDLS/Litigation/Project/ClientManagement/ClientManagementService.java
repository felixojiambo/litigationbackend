package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.ClientManagement.dtos.ClientManagementDTO;
import com.LDLS.Litigation.Project.ClientManagement.messages.ClientManagementMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@Service
@Slf4j
@Configuration
public class ClientManagementService {
    private final ClientManagementMessageProducer clientManagementMessageProducer;
    @Autowired
    private ClientManagementRepository clientManagementRepository;

    public ClientManagementService(ClientManagementMessageProducer clientManagementMessageProducer) {
        this.clientManagementMessageProducer = clientManagementMessageProducer;
    }

    public long countActiveClients() {
        return clientManagementRepository.countByStatus("active");
    }

    public long countPendingClients() {
        return clientManagementRepository.countByStatus("pending");
    }

    public long countTotalClients() {
        // Assuming "total" means all clients regardless of status
        return clientManagementRepository.count();
    }

    public long countLitigationClients() {
        return clientManagementRepository.countByStatus("litigation");
    }

    public List<ClientManagement> getActiveClients() {
        return clientManagementRepository.findByStatus("active");
    }

    public List<ClientManagement> getPendingClients() {
        return clientManagementRepository.findByStatus("pending");
    }

    public List<ClientManagement> getLitigationClients() {
        return clientManagementRepository.findByStatus("litigation");
    }

    // Assuming "total" means all clients regardless of status
    public List<ClientManagement> getTotalClients() {
        return clientManagementRepository.findAll();
    }

    public List<ClientManagement> searchByClientOrLoan(String clientCode, Long loanAccNo) {
        if (clientCode == null && loanAccNo == null) {
            throw new IllegalArgumentException("At least one of clientCode or loanAccNo must be provided.");
        }
        return clientManagementRepository.findByClientCodeOrLoanAccNo(clientCode, loanAccNo);
    }


    public EntityResponse assignOfficerToClient(Long id, ClientRequest request) {
        EntityResponse response = new EntityResponse<>();
        try {
            ClientManagement client = clientManagementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setOfficer(request.getOfficer());
            client.setAdditionalInfo(request.getAdditionalInfo());
            client.setPriority(request.getPriority());
            client.setDepartment(request.getDepartment());
            client.setDeadline(request.getDeadline());
            client.setStatus("Active");
            clientManagementRepository.save(client);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Client Assigned Successfully!");
            response.setEntity(client);
        }catch (RuntimeException e) {
            System.err.println("Error assigning officer to client: " + e.getMessage());
        }
        return response;
    }

    public EntityResponse add(ClientManagementDTO clientManagementDTO) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            String dayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMM"));
            String randomDigits = String.format("%04d", new Random().nextInt(10000));
            String clientCode = "CAD" + "/" + dayMonth + "/" + randomDigits;
            clientManagementDTO.setClientCode(clientCode);
            clientManagementDTO.setStatus("Pending");
            ClientManagement clientManagement = convertDTOToEntity(clientManagementDTO);
            ClientManagement savedCase = clientManagementRepository.save(clientManagement);
            entityResponse.setMessage("Client added successfully");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            entityResponse.setEntity(savedCase);

            // Call transferClientToLitigation after the client is successfully added
            transferClientToLitigation(clientCode);

        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while adding client: {}", e.getMessage(), e);
            entityResponse.setMessage("Failed to add client due to data integrity issue");
            entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            log.error("Error occurred while adding client: {}", e.getMessage(), e);
            entityResponse.setMessage("Failed to add client");
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return entityResponse;
    }

    public void transferClientToLitigation(String clientCode) {
        try {
            ClientManagement client = clientManagementRepository.findByClientCode(clientCode)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setStatus("Litigation");
            clientManagementRepository.save(client);
            ClientManagementDTO clientManagementDTO = convertToDTO(client);
            clientManagementMessageProducer.sendMessage(clientManagementDTO);
            System.out.println("Client " + clientCode + " has been transferred to Litigation.");
        } catch (RuntimeException e) {
            System.err.println("Error transferring client to Litigation: " + e.getMessage());
        }
    }

//    public void transferClientToLitigation(String clientCode) {
//        try {
//            ClientManagement client = clientManagementRepository.findByClientCode(clientCode)
//                    .orElseThrow(() -> new RuntimeException("Client not found"));
//            client.setStatus("Litigation");
//            clientManagementRepository.save(client);
//            ClientManagementDTO clientManagementDTO = convertToDTO(client);
//            clientManagementMessageProducer.sendMessage(clientManagementDTO);
//            System.out.println("Client " + clientCode + " has been transferred to Litigation.");
//        } catch (RuntimeException e) {
//            System.err.println("Error transferring client to Litigation: " + e.getMessage());
//        }
//    }


    public ClientManagementDTO convertToDTO(ClientManagement clientManagement) {
        ClientManagementDTO dto = new ClientManagementDTO();
        dto.setId(clientManagement.getId());
        dto.setFirstName(clientManagement.getFirstName());
        dto.setMiddleName(clientManagement.getMiddleName());
        dto.setLastName(clientManagement.getLastName());
        dto.setIdNumber(clientManagement.getIdNumber());
        dto.setKraPin(clientManagement.getKraPin());
        dto.setEmailAddr(clientManagement.getEmailAddr());
        dto.setPhoneNo1(clientManagement.getPhoneNo1());
        dto.setPhoneNo2(clientManagement.getPhoneNo2());
        dto.setPostalAddress(clientManagement.getPostalAddress());
        dto.setPostalCode(clientManagement.getPostalCode());
        dto.setCity(clientManagement.getCity());
        dto.setCountry(clientManagement.getCountry());
        dto.setLoanAmount(clientManagement.getLoanAmount());
        dto.setClientCode(clientManagement.getClientCode());
        dto.setLoanAccNo(clientManagement.getLoanAccNo());
        dto.setOriginalLoanAmount(clientManagement.getOriginalLoanAmount());
        dto.setInterestRate(clientManagement.getInterestRate());
        dto.setStartDate(clientManagement.getStartDate());
        dto.setEndDate(clientManagement.getEndDate());
        dto.setOutPrincipal(clientManagement.getOutPrincipal());
        dto.setOutInterest(clientManagement.getOutInterest());
        dto.setLastAccrualDate(clientManagement.getLastAccrualDate());
        dto.setLastPaymentDate(clientManagement.getLastPaymentDate());
        dto.setLastPayReceived(clientManagement.getLastPayReceived());
        dto.setLastIntAppDate(clientManagement.getLastIntAppDate());
        dto.setUserAssetClass(clientManagement.getUserAssetClass());
        dto.setClassificationDate(clientManagement.getClassificationDate());
        dto.setLoanTenor(clientManagement.getLoanTenor());
        dto.setDefaultReason(clientManagement.getDefaultReason());
        dto.setDefaultDate(clientManagement.getDefaultDate());
        dto.setTransferringOffice(clientManagement.getTransferringOffice());
        dto.setAmountRemaining(clientManagement.getAmountRemaining());
        dto.setLoanDescription(clientManagement.getLoanDescription());
        dto.setDepartment(clientManagement.getDepartment());
        dto.setOfficer(clientManagement.getOfficer());
        dto.setDeadline(clientManagement.getDeadline());
        dto.setPriority(clientManagement.getPriority());
        dto.setAdditionalInfo(clientManagement.getAdditionalInfo());
        dto.setTotalClients(clientManagement.getTotalClients());
        dto.setActiveClients(clientManagement.getActiveClients());
        dto.setPendingClients(clientManagement.getPendingClients());
        dto.setLitigationClients(clientManagement.getLitigationClients());
        dto.setStatus(clientManagement.getStatus());
        dto.setDetails(clientManagement.getDetails());
        dto.setLastUpdated(LocalDateTime.now()); // Assuming you want to set the last updated time here
        return dto;
    }

    public ClientManagement convertDTOToEntity(ClientManagementDTO clientManagementDTO) {
        ClientManagement clientManagement = new ClientManagement();
        clientManagement.setId(clientManagementDTO.getId());
        clientManagement.setFirstName(clientManagementDTO.getFirstName());
        clientManagement.setMiddleName(clientManagementDTO.getMiddleName());
        clientManagement.setLastName(clientManagementDTO.getLastName());
        clientManagement.setIdNumber(clientManagementDTO.getIdNumber());
        clientManagement.setKraPin(clientManagementDTO.getKraPin());
        clientManagement.setEmailAddr(clientManagementDTO.getEmailAddr());
        clientManagement.setPhoneNo1(clientManagementDTO.getPhoneNo1());
        clientManagement.setPhoneNo2(clientManagementDTO.getPhoneNo2());
        clientManagement.setPostalAddress(clientManagementDTO.getPostalAddress());
        clientManagement.setPostalCode(clientManagementDTO.getPostalCode());
        clientManagement.setCity(clientManagementDTO.getCity());
        clientManagement.setCountry(clientManagementDTO.getCountry());
        clientManagement.setLoanAmount(clientManagementDTO.getLoanAmount());
        clientManagement.setClientCode(clientManagementDTO.getClientCode());
        clientManagement.setLoanAccNo(clientManagementDTO.getLoanAccNo());
        clientManagement.setOriginalLoanAmount(clientManagementDTO.getOriginalLoanAmount());
        clientManagement.setInterestRate(clientManagementDTO.getInterestRate());
        clientManagement.setStartDate(clientManagementDTO.getStartDate());
        clientManagement.setEndDate(clientManagementDTO.getEndDate());
        clientManagement.setOutPrincipal(clientManagementDTO.getOutPrincipal());
        clientManagement.setOutInterest(clientManagementDTO.getOutInterest());
        clientManagement.setLastAccrualDate(clientManagementDTO.getLastAccrualDate());
        clientManagement.setLastPaymentDate(clientManagementDTO.getLastPaymentDate());
        clientManagement.setLastPayReceived(clientManagementDTO.getLastPayReceived());
        clientManagement.setLastIntAppDate(clientManagementDTO.getLastIntAppDate());
        clientManagement.setUserAssetClass(clientManagementDTO.getUserAssetClass());
        clientManagement.setClassificationDate(clientManagementDTO.getClassificationDate());
        clientManagement.setLoanTenor(clientManagementDTO.getLoanTenor());
        clientManagement.setDefaultReason(clientManagementDTO.getDefaultReason());
        clientManagement.setDefaultDate(clientManagementDTO.getDefaultDate());
        clientManagement.setTransferringOffice(clientManagementDTO.getTransferringOffice());
        clientManagement.setAmountRemaining(clientManagementDTO.getAmountRemaining());
        clientManagement.setLoanDescription(clientManagementDTO.getLoanDescription());
        clientManagement.setDepartment(clientManagementDTO.getDepartment());
        clientManagement.setOfficer(clientManagementDTO.getOfficer());
        clientManagement.setDeadline(clientManagementDTO.getDeadline());
        clientManagement.setPriority(clientManagementDTO.getPriority());
        clientManagement.setAdditionalInfo(clientManagementDTO.getAdditionalInfo());
        clientManagement.setTotalClients(clientManagementDTO.getTotalClients());
        clientManagement.setActiveClients(clientManagementDTO.getActiveClients());
        clientManagement.setPendingClients(clientManagementDTO.getPendingClients());
        clientManagement.setLitigationClients(clientManagementDTO.getLitigationClients());
        clientManagement.setStatus(clientManagementDTO.getStatus());
        clientManagement.setDetails(clientManagementDTO.getDetails());
        clientManagement.setLastUpdated(clientManagementDTO.getLastUpdated());
        return clientManagement;
    }


    public EntityResponse update(ClientManagement clientManagement) {
        EntityResponse entityResponse = new EntityResponse<>();
        Optional<ClientManagement> existingCase = clientManagementRepository.findById(clientManagement.getId());
        try {
            if (existingCase.isPresent()) {
                ClientManagement clientManagement1 = existingCase.get();
                clientManagement1.setLoanDescription(clientManagement.getLoanDescription());
                clientManagement1.setLoanAmount(clientManagement.getLoanAmount());
                clientManagement1.setDefaultDate(clientManagement.getDefaultDate());
                clientManagement1.setAmountRemaining(clientManagement.getAmountRemaining());
                clientManagementRepository.save(clientManagement1);
                entityResponse.setMessage("Client updated successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setEntity(clientManagement);

            } else {
                entityResponse.setMessage("client id not found");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e) {
            entityResponse.setMessage("An error has occurred while trying to update a client {}" + e);
            entityResponse.setEntity("");
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return entityResponse;
    }

    public EntityResponse read(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();
        Optional<ClientManagement> clientManagementOptional = clientManagementRepository.findById(id);
        try {
            if (clientManagementOptional.isPresent()) {
                entityResponse.setMessage("case retrieved successfully" + id);
                entityResponse.setStatusCode(HttpStatus.FOUND.value());
                entityResponse.setEntity(clientManagementOptional);

            } else {
                entityResponse.setMessage("case NOT found!");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e) {
            log.error("Error {}", e);
        }
        return entityResponse;

    }
    public EntityResponse delete(Long id){
        EntityResponse entityResponse =new EntityResponse<>();
        Optional<ClientManagement> caseManagementOptional = clientManagementRepository.findById(id);
        try{
            if (caseManagementOptional.isPresent()){
                clientManagementRepository.deleteById(id);
                entityResponse.setMessage("Client deleted successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setEntity("");
            } else {
                entityResponse.setMessage("Client not found");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e){
            log.error("Error occurred", e);

        }
        return entityResponse;
    }
    public List<ClientManagement> findAllClients() {
        return clientManagementRepository.findAll();
    }

    //Manually Map fields from ClientManagement to ClientManagementDTO
    // need to change later

}
