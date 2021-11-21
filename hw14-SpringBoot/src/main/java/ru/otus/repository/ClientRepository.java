package ru.otus.repository;

import ru.otus.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

}
