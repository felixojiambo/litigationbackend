//package com.emtech.Litigation.listeners;
//import com.emtech.Litigation.dtos.ClientManagementDTO;
//import com.emtech.Litigation.services.LitigationCaseService;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CaseMessageListener {
//
//    private final LitigationCaseService litigationCaseInitiator;
//
//    @Autowired
//    public CaseMessageListener(LitigationCaseService litigationCaseInitiator) {
//        this.litigationCaseInitiator = litigationCaseInitiator;
//    }
//
//    @RabbitListener(queues = "newCaseQueue")
//    public void handleNewCase(ClientManagementDTO litigationCaseDTO) {
//        litigationCaseInitiator.processClientData(litigationCaseDTO);
//    }
//}
