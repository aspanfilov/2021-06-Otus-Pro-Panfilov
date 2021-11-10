package ru.otus.web.dto;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.List;

public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private String phones;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = getAddressView(client.getAddress());
        this.phones = getPhonesView(client.getPhones());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        return phones;
    }

    private String getAddressView(Address address) {
        String addressView =
                (address.getCountry() == null ? "" : address.getCountry() + ", ") +
                (address.getCity() == null ? "" : address.getCity() + ", ") +
                (address.getStreet() == null ? "" : address.getStreet() + ", ") +
                (address.getHouseNumber() == 0 ? "" : address.getHouseNumber()) +
                (address.getBuildingNumber() == 0 ? "" : "-" + address.getBuildingNumber()) +
                (address.getApartmentNumber() == 0 ? "" : "-" + address.getApartmentNumber());

        return addressView;
    }

    private String getPhonesView(List<Phone> phones) {
        StringBuilder PhonesView = new StringBuilder();
        for (int i = 0; i < phones.size(); i++) {
            PhonesView.
                    append(phones.get(i).getNumber()).
                    append(i == phones.size() - 1 ? "" : ", ");
        }
        return PhonesView.toString();
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}
