package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DBServiceClientCachedImpl extends DBServiceClientDecorator{

    private static final Logger log = LoggerFactory.getLogger(DBServiceClientCachedImpl.class);

    private final HwCache<String, Client> cache;

    public DBServiceClientCachedImpl(DBServiceClient dbServiceClient, HwCache<String, Client> cache) {
        super(dbServiceClient);
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        Client savedClient = this.dbServiceClient.saveClient(client);
        this.cache.put(getKey(savedClient), savedClient);
        log.info("client saved in cache");
        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client clientFromCache = this.cache.get(String.valueOf(id));
        if (clientFromCache != null) {
            log.info("client got from cache");
            return Optional.of(clientFromCache);
        }
        Optional<Client> clientFromDB = this.dbServiceClient.getClient(id);
        clientFromDB.ifPresent(client -> this.cache.put(getKey(client), client));
        return clientFromDB;
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }

    private String getKey(Client client) {
        return client.getId().toString();
    }
}
