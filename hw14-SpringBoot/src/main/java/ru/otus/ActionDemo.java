package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.service.DBServiceClient;

import java.util.ArrayList;
import java.util.Collections;

@Component("actionDemo")
public class ActionDemo {

    private static final Logger log = LoggerFactory.getLogger(ActionDemo.class);

    private final ClientRepository clientRepository;
    private final DBServiceClient dbServiceClient;

    public ActionDemo(ClientRepository clientRepository,
                      DBServiceClient dbServiceClient) {
        this.clientRepository = clientRepository;
        this.dbServiceClient = dbServiceClient;
    }

    void action() {
//        log.info(">>> client creation");
//        dbServiceClient.saveClient(
//                new Client(
//                        "dbServiceFirst",
//                        new Address("Russia", 2),
//                        new ArrayList<>(Collections.singleton(new Phone("123")))));

//        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
//        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
//                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
//        log.info("clientSecondSelected:{}", clientSecondSelected);
/////
//        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));
//        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
//                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
//        log.info("clientUpdated:{}", clientUpdated);
//
//        log.info("All clients");
//        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));

    }

}
