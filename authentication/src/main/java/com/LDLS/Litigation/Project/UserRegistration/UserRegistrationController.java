package com.LDLS.Litigation.Project.UserRegistration;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistrationService;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistrationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/userRegistration")
@CrossOrigin

public class UserRegistrationController {
    @Autowired
    private UserRegistrationService userRegistrationService;

    @PostMapping("/create")
    public ResponseEntity<EntityResponse> createUserRegistration(@RequestBody UserRegistration userRegistration) {
        try {
            EntityResponse response = userRegistrationService.createUserRegistration(userRegistration);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserRegistration> getuserRegistrationById(@PathVariable Long id) {
        return userRegistrationService.getUserRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fetch")
    public List<UserRegistration> getAllUserRegistration() {
        return userRegistrationService.getAllCustomerRegistration();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserRegistration> updateUserRegistration(
            @PathVariable Long id, @RequestBody UserRegistration userRegistration)
    {
        UserRegistration updatedUserRegistration =
                userRegistrationService.updateUserRegistration(id, userRegistration);
        return ResponseEntity.ok(updatedUserRegistration);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EntityResponse> deleteUserRegistration(@PathVariable Long id) {
        EntityResponse response = userRegistrationService.deleteUserRegistrationById(id);
        return ResponseEntity.ok(response);
    }
}
