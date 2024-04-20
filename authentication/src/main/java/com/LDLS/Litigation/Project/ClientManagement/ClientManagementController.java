package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.ClientManagement.dtos.ClientManagementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("api/v1/clientmanagement")
public class ClientManagementController {
    @Autowired
    ClientManagementService clientManagementService;


    @PostMapping("/add")
    public ResponseEntity<EntityResponse> add(@RequestBody ClientManagementDTO clientManagementDTO) {
        EntityResponse entityResponse = clientManagementService.add(clientManagementDTO);
        return ResponseEntity.status(entityResponse.getStatusCode()).body(entityResponse);
    }

    @PutMapping("/update/{id}")
    public EntityResponse update(@PathVariable Long id, @RequestBody ClientManagement clientManagement) {
        clientManagement.setId(id);
        return clientManagementService.update(clientManagement);
    }

    @GetMapping("/read")
    public EntityResponse read(@RequestParam Long id) {

        return clientManagementService.read(id);
    }

    @GetMapping("/fetch")
    public List<ClientManagement> fetch() {
        return clientManagementService.findAllClients();
    }

    @DeleteMapping("/delete/{id}")
    public EntityResponse delete(@RequestParam Long id) {
        return clientManagementService.delete(id);
    }

    @PostMapping("/assign")
    public EntityResponse assignCase(@RequestParam Long id, @RequestBody ClientRequest request){
        return clientManagementService.assignOfficerToClient(id, request);
    }

    @GetMapping("/search")
    public List<ClientManagement> search(@RequestParam(required = false) String clientCode, @RequestParam(required = false) Long loanAccNo) {
        return clientManagementService.searchByClientOrLoan(clientCode, loanAccNo);
    }



    //specified the type parameter for EntityResponse in your controller
    //to update other responses as well
    @PostMapping("/transfer-to-litigation")
    public EntityResponse<String> transferClientToLitigation(@RequestParam(required = false) String clientCode) {
        EntityResponse<String> response = new EntityResponse<>();
        if (clientCode == null || clientCode.isEmpty()) {
            response.setMessage("The 'clientCode' parameter is required.");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        try {
            clientManagementService.transferClientToLitigation(clientCode);
            response.setMessage("Client transferred to Litigation successfully.");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.OK.value());
        } catch (RuntimeException e) {
            response.setMessage("Error transferring client to Litigation: " + e.getMessage());
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
}
