package com.LDLS.Litigation.Project.Authentication.Roles;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public EntityResponse add(Role role) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            Role savedRole = roleRepository.save(role);
            entityResponse.setMessage("role created successfully");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            entityResponse.setEntity(savedRole);
        }catch (Exception e){
            log.error("An error has occurred while trying to create a ROLE! {} ", e);
        }
        return entityResponse;
    }
}

