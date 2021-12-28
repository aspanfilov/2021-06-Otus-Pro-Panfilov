package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public abstract class DBServiceClientDecorator implements DBServiceClient{

    protected DBServiceClient dbServiceClient;

    public DBServiceClientDecorator(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public Client saveClient(Client client) {
        return dbServiceClient.saveClient(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        return dbServiceClient.getClient(id);
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }

}
