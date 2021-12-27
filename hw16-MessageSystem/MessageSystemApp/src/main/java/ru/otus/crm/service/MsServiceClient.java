package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.crm.dto.ClientData;
import ru.otus.crm.dto.ClientListData;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@Service
public class MsServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(MsServiceClient.class);

    private final MsClient frontendMsClient;
    private final MsClient databaseMsClient;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MsServiceClient(
            @Qualifier("frontendMessageService") MsClient frontendMsClient,
            @Qualifier("databaseMessageClient") MsClient databaseMsClient,
            SimpMessagingTemplate simpMessagingTemplate) {

        this.frontendMsClient = frontendMsClient;
        this.databaseMsClient = databaseMsClient;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void produceAndSendMessageGetClients(String responseDestination) {
        Message<ClientListData> outMsg = this.frontendMsClient.produceMessage(
                this.databaseMsClient.getName(),
                new ClientListData(),
                MessageType.GET_CLIENTS,
                responseMsg -> {
                    logger.info("CALLBACK");
                    this.simpMessagingTemplate.convertAndSend(
                            responseDestination,
                            responseMsg.getClientList());

                });
        this.frontendMsClient.sendMessage(outMsg);
    }

    public void produceAndSendMessageSaveClient(ClientData clientData) {
        Message<ClientData> outMsg = frontendMsClient.produceMessage(
                databaseMsClient.getName(),
                clientData,
                MessageType.SAVE_CLIENT,
                responseMsg -> {
                    logger.info("CALLBACK: REDIRECT TO CLIENTS()");
                    clients();
                });
        frontendMsClient.sendMessage(outMsg);
    }
}
