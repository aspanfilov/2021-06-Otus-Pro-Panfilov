package ru.otus.crm.service;

import ru.otus.crm.dto.ClientData;

public interface MsServiceClient {

    void produceAndSendMessageGetClients(String responseDestination);

    void produceAndSendMessageSaveClient(String responseDestination, ClientData clientData);

}
