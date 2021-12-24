package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.service.DBServiceAddress;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DBServicePhone;

import java.util.Set;


@Component("actionDemo")
public class ActionDemo {

    private static final Logger log = LoggerFactory.getLogger(ActionDemo.class);

    private final ClientRepository clientRepository;
    private final DBServiceClient dbServiceClient;
    private final DBServiceAddress dbServiceAddress;
    private final DBServicePhone dbServicePhone;

    public ActionDemo(ClientRepository clientRepository,
                      DBServiceClient dbServiceClient,
                      DBServiceAddress dbServiceAddress,
                      DBServicePhone dbServicePhone) {
        this.clientRepository = clientRepository;
        this.dbServiceClient = dbServiceClient;
        this.dbServiceAddress = dbServiceAddress;
        this.dbServicePhone = dbServicePhone;
    }

    void action() {
        log.info(">>> first creation");

        var firstClient = dbServiceClient.save(
                new Client("dbServiceFirst", new Address("Russia", 1)));

        log.info("firstClient: {}", firstClient);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));

        var firstClient2 = dbServiceClient.save(
                new Client(
                        "dbServiceFirst",
                        new Address("Russia", 1),
                        Set.of(new Phone("123"))));

        log.info("firstClient: {}", firstClient);

        var clientSecond = dbServiceClient.save(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.get(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

        dbServiceClient.save(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));
        var clientUpdated = dbServiceClient.get(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));

    }

}
