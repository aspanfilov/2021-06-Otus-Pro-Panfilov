package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client save(Client client);

    Optional<Client> get(long id);

    List<Client> findAll();

}
