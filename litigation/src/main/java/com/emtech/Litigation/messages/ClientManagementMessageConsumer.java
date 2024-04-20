package com.emtech.Litigation.messages;

import com.emtech.Litigation.dtos.ClientManagementDTO;
import com.emtech.Litigation.services.LitigationCaseService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "newCaseQueue")
public class ClientManagementMessageConsumer {
    private final LitigationCaseService litigationCaseService;

    @Autowired
    public ClientManagementMessageConsumer(LitigationCaseService litigationCaseService) {
        this.litigationCaseService = litigationCaseService;
    }

    @RabbitListener(queues = "newCaseQueue")
    public void consumeMessage(ClientManagementDTO clientManagementDTO) {
        litigationCaseService.processClientData(clientManagementDTO);
    }
}
