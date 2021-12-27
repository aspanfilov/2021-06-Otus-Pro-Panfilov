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

    private final MsServiceClient msServiceClient;

    @Autowired
    public ClientController(MsServiceClient msServiceClient) {

        this.msServiceClient = msServiceClient;
    }

    @MessageMapping("/clients")
    public void clients() {
        logger.info("get client list");
        this.msServiceClient.produceAndSendMessageGetClients(
                "/topic/response/clients");
    }

    @MessageMapping("/createClient")
    public void createClient(ClientData clientData) {
        logger.info("create client by DTO {}", clientData);
        this.msServiceClient.produceAndSendMessageSaveClient(
                "/topic/response/createClient",
                clientData);
    }

}
