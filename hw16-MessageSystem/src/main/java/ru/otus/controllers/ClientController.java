package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.HtmlUtils;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.domain.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final DBServiceClient clientService;

    public ClientController(DBServiceClient clientService) {
        this.clientService = clientService;
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
    public void createClient(Client client) {
        logger.info("create client {}", client);
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
