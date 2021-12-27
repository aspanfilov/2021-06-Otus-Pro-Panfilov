package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DBServiceClientCachedImpl implements DBServiceClient{

    private static final Logger log = LoggerFactory.getLogger(DBServiceClientCachedImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final HwCache<String, Client> cache;
    private final TransactionRunner transactionRunner;

    public DBServiceClientCachedImpl(
            DataTemplate<Client> clientDataTemplate,
            HwCache<String, Client> cache,
            TransactionRunner transactionRunner) {

        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
        this.transactionRunner = transactionRunner;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                Long clientId = clientDataTemplate.insert(connection, client);
                Client createdClient = new Client(clientId, client.getName());
                this.cache.put(clientId.toString(), createdClient);

                log.info("created client: {}", createdClient);
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            this.cache.put(client.getId().toString(), client);

            log.info("updated client: {}", client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client client = this.cache.get(String.valueOf(id));
        if (client != null) {
            return Optional.of(client);
        }
        return transactionRunner.doInTransaction(connection -> {
            var clientOptional = clientDataTemplate.findById(connection, id);
            clientOptional.ifPresent(foundClient ->
                    this.cache.put(foundClient.getId().toString(), foundClient));
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
