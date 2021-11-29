package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.crm.model.Client;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.crm.service.DBServiceAddress;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DBServicePhone;


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

        var firstClient = dbServiceClient.saveClient(new Client("first client"));
        var savedClient = dbServiceClient.getClient(firstClient.getId());

//        var firstAddress = dbServiceAddress.saveAddress(
//                new Address("Russia", 4)
//        );
//
//        var savedAddress = dbServiceAddress.getAddress(firstAddress.getAddressId());

//        var firstPhones = dbServicePhone.savePhone(
//                new Phone("123")
//        );

//        var firstClient = dbServiceClient.saveClient(
//                new Client("First client", new Address("Russia", 4))
//        );
//
//        var savedClient = dbServiceClient.getClient(firstClient.getClientId());
//        var savedAddress = dbServiceAddress.getAddress(savedClient.get().getAddress().getAddressId());

//        var firstClient = dbServiceClient.saveClient(
//                new Client("First client", new Address("Russia", 1))
//        );

//        dbServiceClient.saveClient(
//                new Client(
//                        "dbServiceFirst",
//                        new Address("Russia", 2),
//                        List.of(new Phone("123"))));


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
