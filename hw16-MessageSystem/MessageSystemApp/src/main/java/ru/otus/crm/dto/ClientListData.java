package ru.otus.crm.dto;

import ru.otus.crm.model.Client;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class ClientListData implements ResultDataType {

    private final List<ClientDto> clientList;

    public ClientListData(List<ClientDto> clientList) {
        this.clientList = clientList;
    }

    public List<ClientDto> getClientList() {
        return clientList;
    }
}
