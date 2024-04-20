package com.emtech.Litigation.services.impl;

import com.emtech.Litigation.dtos.ClientManagementDTO;
import com.emtech.Litigation.models.LitigationCase;
import com.emtech.Litigation.repositories.LitigationCaseRepository;
import com.emtech.Litigation.services.LitigationCaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LitigationCaseServiceImpl implements LitigationCaseService {
    @Autowired
    private LitigationCaseRepository litigationCaseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LitigationCaseServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        WebClient webClient = WebClient.create("http:// 192.168.42.95:7800");
        // Removed Validator and ValidatorFactory initialization
    }

    @Override
    public void processClientData(ClientManagementDTO clientManagementDTO) {
        try {
            LitigationCase litigationCase = modelMapper.map(clientManagementDTO, LitigationCase.class);
            litigationCaseRepository.save(litigationCase);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process client data", e);
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
