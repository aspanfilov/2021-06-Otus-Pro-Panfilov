package ru.otus.crm.dto;

import ru.otus.crm.model.Client;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class ClientListData implements ResultDataType {

    private final List<ClientData> clientList;

    public ClientListData() {
        this.clientList = null;
    }

    public ClientListData(List<ClientData> clientList) {
        this.clientList = clientList;
    }

    public List<ClientData> getClientList() {
        return clientList;
    }
}
