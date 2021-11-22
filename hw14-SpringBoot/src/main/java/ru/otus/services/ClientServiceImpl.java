package ru.otus.services;


import org.springframework.stereotype.Service;
import ru.otus.domain.Client;
import ru.otus.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Client saveClient(Client client) {
        return clientRepository.saveClient(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        return clientRepository.getClient(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
