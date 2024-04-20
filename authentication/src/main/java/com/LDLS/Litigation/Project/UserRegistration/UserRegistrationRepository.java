package com.LDLS.Litigation.Project.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
    Optional<UserRegistration> findByNationalIdNumber(String nationalIdNumber);
}
