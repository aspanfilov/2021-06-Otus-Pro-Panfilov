package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.handlers.GetClientsRequestHandler;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    зкш

    public ClientController(DBServiceClient clientService) {

        var messageSystem = new MessageSystemImpl();

        var requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_CLIENTS, new GetClientsRequestHandler(clientService));
//        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_CLIENT, new GetUserDataRequestHandler(new DBServiceImpl()));
        var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

//        var requestHandlerFrontendStore = new HandlersStoreImpl();
//        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler());
//        var frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);
//        messageSystem.addClient(frontendMsClient);

    }

    @MessageMapping("/clients")
    @SendTo("/topic/response")
    public Message clients() {
        logger.info("get client list");
        List<Client> clientList = clientService.findAll();
        List<ClientDto> clientDtoList = clientList.stream().map(ClientDto::new).collect(Collectors.toList());
        return new Message(clientDtoList);
    }

    @MessageMapping("/createClient")
    public void createClient(ClientDto clientDto) {
        logger.info("create client {}", clientDto);
        clientService.save(clientDto.toClient());

    }

//    @MessageMapping("/message")
//    @SendTo("/topic/currentTime")
//    public Message getMessage(@DestinationVariable Message message) {
//        logger.info("got message:{}", message);
//        return new Message(HtmlUtils.htmlEscape(message.getMessageStr()));
//    }

//    @GetMapping({"/", "/client/list"})
//    public String clientsListView(Model model) {
//        List<Client> clientList = clientService.findAll();
//        List<ClientDto> clientDtoList = clientList.stream().map(ClientDto::new).collect(Collectors.toList());
//
//        model.addAttribute("clients", clientDtoList);
//        return "clientsList.html";
//    }

//    @GetMapping("/client/create")
//    public String clientCreateView(Model model) {
//        model.addAttribute("clientDto", new ClientDto());
//        return "clientCreate.html";
//    }
//
//    @PostMapping("/client/save")
//    public RedirectView clientSave(@ModelAttribute ClientDto clientDto) {
//        clientService.save(clientDto.toClient());
//        return new RedirectView("/", true);
//    }

}
