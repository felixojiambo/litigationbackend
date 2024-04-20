package com.LDLS.Litigation.Project.Authentication;
import com.LDLS.Litigation.Project.Authentication.Roles.ERole;
import com.LDLS.Litigation.Project.Authentication.Roles.Role;
import com.LDLS.Litigation.Project.Authentication.Roles.RoleRepository;
import com.LDLS.Litigation.Project.Authentication.Users.Users;
import com.LDLS.Litigation.Project.Authentication.Users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
@Component
@Slf4j
public class StartupInitializer implements ApplicationRunner {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSuperUser();
    }

    private void createSuperUser() {
        log.info("------->>>>>> CREATING SUPERADMIN <<<<<<-------------");

        if (userRepository.existsByUsername("superAdmin")) {
            log.info("username exists!.Skipping creation");
        }else if (userRepository.existsByEmail("superAdmin@example.com")) {
            log.info("Admin  with email SuperAdmin@example.com already exists. Skipping creation.");
        }else {
//            Role superUserRole = roleRepository.findByName(ERole.ROLE_SUPERUSER.toString())
            Role superAdminRole = new Role();
            superAdminRole.setName(ERole.ADMIN.toString());
            roleRepository.save(superAdminRole);
            log.info("Role found!");
            Users superAdmin = new Users();
            superAdmin.setUsername("superAdmin");
            superAdmin.setEmail("superAdmin@example.com");
            superAdmin.setPassword(passwordEncoder.encode("LITIGATION"));
            superAdmin.setRoles(Collections.singleton(superAdminRole));
            superAdmin.setAcctActive(true);
            superAdmin.setAcctLocked(false);
            superAdmin.setStatus("ACTIVE");
            log.info("username = superAdmin");
            log.info("The superAdmin  log in password is = LITIGATION ");
            superAdmin.setVerifiedFlag('Y');
            superAdmin.setFirstLogin('Y');
            superAdmin.setVerifiedOn(new Date());
            superAdmin.setEmail("superAdmin@example.com");
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setPhoneNo("2547123456789");

            userRepository.save(superAdmin);
        }
    }
}