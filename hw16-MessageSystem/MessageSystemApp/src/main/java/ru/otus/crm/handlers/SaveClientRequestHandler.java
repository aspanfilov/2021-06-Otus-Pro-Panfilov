package ru.otus.crm.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.dto.ClientData;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;

import java.util.Optional;

public class SaveClientRequestHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(SaveClientRequestHandler.class);

    private final DBServiceClient dbServiceClient;

    public SaveClientRequestHandler(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        try {
            logger.info("save client");
            ClientData clientData = (ClientData) msg.getData();
            this.dbServiceClient.save(clientData.toClient());
            return Optional.empty();
        } catch (Exception e) {
            logger.info("error: save client");
            return Optional.empty();
        }
    }
}
