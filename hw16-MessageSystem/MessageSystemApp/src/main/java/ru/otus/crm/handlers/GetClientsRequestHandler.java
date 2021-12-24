package ru.otus.crm.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.dto.ClientListData;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetClientsRequestHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(GetClientsRequestHandler.class);

    private final DBServiceClient clientService;

    public GetClientsRequestHandler(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @Override
    public <T extends ResultDataType> Optional<Message<T>> handle(Message<T> msg) {
        try {
            logger.info("get client list");
            List<Client> clientList = clientService.findAll();
            List<ClientDto> clientDtoList = clientList.stream().map(ClientDto::new).collect(Collectors.toList());
            ClientListData clientListData = new ClientListData(clientDtoList);
            return Optional.of(MessageBuilder.buildReplyMessage(msg, (T) clientListData));
        } catch (Exception e) {
            logger.info("error: get client list", e);
            return Optional.empty();
        }
    }
}
