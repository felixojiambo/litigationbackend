package com.LDLS.Litigation.Project.UserRegistration;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class UserRegistrationService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserRegistration> getAllCustomerRegistration() {
        return userRegistrationRepository.findAll();
    }

    public Optional<UserRegistration> getUserRegistrationById(Long id) {
        return userRegistrationRepository.findById(id);
    }

    public EntityResponse createUserRegistration(UserRegistration userRegistration) {
        EntityResponse response = new EntityResponse<>();
        try {
            Optional<UserRegistration> user = userRegistrationRepository.findByNationalIdNumber(userRegistration.getNationalIdNumber());
            if (user.isPresent()) {
                response.setMessage("Provided user National Id already exists!!");
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());

            } else {
                String randomPassword = passwordEncoder.encode("randomPassword");
                userRegistration.setTemporaryPassword(randomPassword);
                userRegistration.setUsername(userRegistration.getUsername());
                userRegistration.setRole(userRegistration.getRole());
                UserRegistration registration = userRegistrationRepository.save(userRegistration);
                response.setMessage("successfully registered Customer");
                response.setEntity(registration);
                response.setStatusCode(HttpStatus.CREATED.value());
            }

        } catch (Exception e) {
            log.info("Unable to register", e.getMessage());
            response.setMessage("failed to register user");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    public UserRegistration updateUserRegistration(Long id, UserRegistration newUserRegistration) {
        return userRegistrationRepository.findById(id)
                .map(userRegistration -> {
                    userRegistration.setFirstName(newUserRegistration.getFirstName());
                    userRegistration.setMiddleName(newUserRegistration.getMiddleName());
                    userRegistration.setLastName(newUserRegistration.getLastName());
                    userRegistration.setTemporaryPassword(newUserRegistration.getTemporaryPassword());
                    userRegistration.setEmail(newUserRegistration.getEmail());
                    userRegistration.setNationalIdNumber(newUserRegistration.getNationalIdNumber());
                    userRegistration.setRole(newUserRegistration.getRole());
                    userRegistration.setPhoneNumber(newUserRegistration.getPhoneNumber());
                    userRegistration.setGender(newUserRegistration.getGender());
                    userRegistration.setAccessPeriod(newUserRegistration.getAccessPeriod());
                    userRegistration.setBranch(newUserRegistration.getBranch());
                    userRegistration.setCountry(newUserRegistration.getCountry());

                    return userRegistrationRepository.save(userRegistration);
                })
                .orElseThrow(() -> new UserNotFoundException("Customer registration not found with id: " + id));
    }
    public EntityResponse deleteUserRegistrationById(Long id) {
        EntityResponse res = new EntityResponse<>();
        try {
            Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(id);
            if (optionalUserRegistration.isPresent()) {
                userRegistrationRepository.deleteById(id);
                res.setMessage("User deleted successfully");
                res.setStatusCode(HttpStatus.OK.value());
            } else {
                res.setMessage("User not found");
                res.setStatusCode(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.error("Exception", e);
            res.setMessage("Error deleting user");
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return res;
    }
}