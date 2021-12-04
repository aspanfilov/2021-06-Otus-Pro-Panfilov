package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    private final DBServiceClient clientService;

    public ClientController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clientList = clientService.findAll();
        List<ClientDto> clientDtoList = clientList.stream().map(ClientDto::new).collect(Collectors.toList());

        model.addAttribute("clients", clientDtoList);
        return "clientsList.html";
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("clientDto", new ClientDto());
        return "clientCreate.html";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(@ModelAttribute ClientDto clientDto) {
        clientService.save(clientDto.toClient());
        return new RedirectView("/", true);
    }

}
