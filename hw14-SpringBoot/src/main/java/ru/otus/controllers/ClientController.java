package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.domain.Client;
import ru.otus.dto.ClientDto;
import ru.otus.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        List<ClientDto> clientsDto = clients.stream().map(ClientDto::new).collect(Collectors.toList());
        model.addAttribute("clients", clientsDto);
        return "clientsList";
    }

//    @GetMapping("/client/create")
//    public String clientCreateView(Model model) {
//        model.addAttribute("client", new Client());
//        return "clientCreate";
//    }
//
//    @PostMapping("/client/save")
//    public RedirectView clientSave(@ModelAttribute Client client) {
//        clientService.saveClient(client);
//        return new RedirectView("/", true);
//    }

}
