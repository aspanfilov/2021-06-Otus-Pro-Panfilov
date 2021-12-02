package ru.otus.crm.dto;

import org.springframework.stereotype.Component;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.Set;

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
        if (address == null) return "";
        String addressView =
                (address.getCountry() == null ? "" : address.getCountry() + ", ") +
                        (address.getCity() == null ? "" : address.getCity() + ", ") +
                        (address.getStreet() == null ? "" : address.getStreet() + ", ") +
                        (address.getHouseNumber() == 0 ? "" : address.getHouseNumber()) +
                        (address.getBuildingNumber() == 0 ? "" : "-" + address.getBuildingNumber()) +
                        (address.getApartmentNumber() == 0 ? "" : "-" + address.getApartmentNumber());

        return addressView;
    }

    private String getPhonesView(Set<Phone> phones) {
        if (phones.isEmpty()) return "";

        var phonesView = new StringBuilder();
        for (Phone phone : phones) {
            phonesView.append(phone.getNumber()).append(", ");
        }
        phonesView.delete(phonesView.length()-2, phonesView.length()-1);
        return phonesView.toString();
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
