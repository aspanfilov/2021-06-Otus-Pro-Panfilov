package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.crm.dto.ClientData;
import ru.otus.crm.dto.ClientListData;
import ru.otus.crm.service.MsServiceClient;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final MsClient frontendMsClient;
    private final MsClient databaseMsClient;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final MsServiceClient msServiceClient;

    @Autowired
    public ClientController(@Qualifier("frontendMessageService") MsClient frontendMsClient,
                            @Qualifier("databaseMessageClient") MsClient databaseMsClient,
                            SimpMessagingTemplate simpMessagingTemplate,
                            MsServiceClient msServiceClient) {

        this.frontendMsClient = frontendMsClient;
        this.databaseMsClient = databaseMsClient;
        this.simpMessagingTemplate = simpMessagingTemplate;

        this.msServiceClient = msServiceClient;
    }

    @MessageMapping("/clients")
    public void clients() {
        logger.info("get client list");
        this.msServiceClient.produceAndSendMessageGetClients("/topic/response");
    }

    @MessageMapping("/createClient")
    public void createClient(ClientData clientData) {
        logger.info("create client by DTO {}", clientData);
        this.msServiceClient.produceAndSendMessageSaveClient(clientData);

    }

//    @MessageMapping("/clients")
//    public void clients() {
//        logger.info("get client list");
//        Message<ClientListData> outMsg = frontendMsClient.produceMessage(
//                databaseMsClient.getName(),
//                new ClientListData(),
//                MessageType.GET_CLIENTS,
//                responseMsg -> {
//                    logger.info("CALLBACK");
//                    simpMessagingTemplate.convertAndSend(
//                            "/topic/response",
//                            responseMsg.getClientList());
//
//                });
//        frontendMsClient.sendMessage(outMsg);
//    }
//
//    @MessageMapping("/createClient")
//    public void createClient(ClientData clientData) {
//        logger.info("create client by DTO {}", clientData);
//        Message<ClientData> outMsg = frontendMsClient.produceMessage(
//                databaseMsClient.getName(),
//                clientData,
//                MessageType.SAVE_CLIENT,
//                responseMsg -> {
//                    logger.info("CALLBACK: REDIRECT TO CLIENTS()");
//                    clients();
//                });
//        frontendMsClient.sendMessage(outMsg);
//    }

}
