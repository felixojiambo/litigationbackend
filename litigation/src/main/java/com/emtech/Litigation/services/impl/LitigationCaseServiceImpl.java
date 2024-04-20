package com.emtech.Litigation.services.impl;

import com.emtech.Litigation.dtos.ClientManagementDTO;
import com.emtech.Litigation.models.LitigationCase;
import com.emtech.Litigation.repositories.LitigationCaseRepository;
import com.emtech.Litigation.services.LitigationCaseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LitigationCaseServiceImpl implements LitigationCaseService {
    @Autowired
    private LitigationCaseRepository litigationCaseRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(LitigationCaseServiceImpl.class);
    @Autowired
    public LitigationCaseServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        WebClient webClient = WebClient.create("http://127.0.0.1:7800");
    }

    @Override
    public void processClientData(ClientManagementDTO clientManagementDTO) {
        if (clientManagementDTO == null) {
            logger.error("ClientManagementDTO is null");
            throw new IllegalArgumentException("ClientManagementDTO cannot be null");
        }

        try {
            LitigationCase litigationCase = modelMapper.map(clientManagementDTO, LitigationCase.class);
            if (litigationCase == null) {
                logger.error("Mapped LitigationCase is null");
                throw new RuntimeException("Failed to map ClientManagementDTO to LitigationCase");
            }

            litigationCaseRepository.save(litigationCase);

            // Compare fields and log unmapped ones
            compareFieldsAndLog(clientManagementDTO, litigationCase);
        } catch (Exception e) {
            logger.error("Failed to process client data", e);
            throw new RuntimeException("Failed to process client data", e);
        }
    }

    private void compareFieldsAndLog(ClientManagementDTO source, LitigationCase target) {
        List<String> unmappedFields = new ArrayList<>();

        for (Field field : source.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object sourceValue = field.get(source);
                Field targetField = target.getClass().getDeclaredField(field.getName());
                targetField.setAccessible(true);
                Object targetValue = targetField.get(target);

                if (sourceValue == null || !sourceValue.equals(targetValue)) {
                    unmappedFields.add(field.getName());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                logger.error("Field comparison failed for field: {}", field.getName(), e);
            }
        }

        if (!unmappedFields.isEmpty()) {
            logger.warn("The following fields were not mapped: {}", unmappedFields);
        } else {
            logger.info("All fields were successfully mapped.");
        }
    }


    @Override
    public ClientManagementDTO getLitigationCaseById(Long id) {
        Optional<LitigationCase> litigationCaseOptional = litigationCaseRepository.findById(id);
        if (litigationCaseOptional.isPresent()) {
            return convertToDto(litigationCaseOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Litigation case not found with ID: " + id);
        }
    }

    @Override
    public List<ClientManagementDTO> getAllLitigationCases() {
        List<LitigationCase> litigationCases = litigationCaseRepository.findAll();
        return litigationCases.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ClientManagementDTO convertToDto(LitigationCase litigationCase) {
        return modelMapper.map(litigationCase, ClientManagementDTO.class);
    }
}
