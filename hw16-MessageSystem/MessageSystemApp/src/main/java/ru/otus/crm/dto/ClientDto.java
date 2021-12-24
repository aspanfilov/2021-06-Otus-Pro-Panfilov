package ru.otus.crm.dto;

import org.springframework.data.annotation.PersistenceConstructor;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.messagesystem.client.ResultDataType;

import java.util.Set;

public class ClientDto {

    private Long id;
    private String name;

    private String addressView;
    private String phonesView;

    private Long addressId;
    private Long addressClientId;
    private String addressCountry;
    private String addressCity;
    private String addressStreet;
    private int addressHouseNumber;
    private int addressBuildingNumber;
    private int addressApartmentNumber;

    private Long phoneId;
    private Long phoneClientId;
    private String phoneNumber;

    public ClientDto() {}

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.addressView = getAddressView(client.getAddress());
        this.phonesView = getPhonesView(client.getPhones());

        Address address = client.getAddress();
        if (address != null) {
            this.addressId = address.getId();
            this.addressClientId = client.getId();
            this.addressCity = address.getCity();
            this.addressStreet = address.getStreet();
            this.addressCountry = address.getCountry();
            this.addressHouseNumber = address.getHouseNumber();
            this.addressBuildingNumber = address.getBuildingNumber();
            this.addressApartmentNumber = address.getApartmentNumber();
        }

        Set<Phone> phones = client.getPhones();
        if (phones != null) {
            phones.stream().findFirst().ifPresent(phone -> {
                this.phoneId = phone.getId();
                this.phoneClientId = client.getId();
                this.phoneNumber = phone.getNumber();
            });
        }
    }

    @PersistenceConstructor
    public ClientDto(
            Long id,
            String name,
            String addressView,
            String phonesView,
            Long addressId,
            Long addressClientId,
            String addressCountry,
            String addressCity,
            String addressStreet,
            int addressHouseNumber,
            int addressBuildingNumber,
            int addressApartmentNumber,
            Long phoneId,
            Long phoneClientId,
            String phoneNumber) {

        this.id = id;
        this.name = name;
        this.addressView = addressView;
        this.phonesView = phonesView;
        this.addressId = addressId;
        this.addressClientId = addressClientId;
        this.addressCountry = addressCountry;
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.addressHouseNumber = addressHouseNumber;
        this.addressBuildingNumber = addressBuildingNumber;
        this.addressApartmentNumber = addressApartmentNumber;
        this.phoneId = phoneId;
        this.phoneClientId = phoneClientId;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressView() {
        return addressView;
    }

    public void setAddressView(String addressView) {
        this.addressView = addressView;
    }

    public String getPhonesView() {
        return phonesView;
    }

    public void setPhonesView(String phonesView) {
        this.phonesView = phonesView;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressClientId() {
        return addressClientId;
    }

    public void setAddressClientId(Long addressClientId) {
        this.addressClientId = addressClientId;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public int getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public void setAddressHouseNumber(int addressHouseNumber) {
        this.addressHouseNumber = addressHouseNumber;
    }

    public int getAddressBuildingNumber() {
        return addressBuildingNumber;
    }

    public void setAddressBuildingNumber(int addressBuildingNumber) {
        this.addressBuildingNumber = addressBuildingNumber;
    }

    public int getAddressApartmentNumber() {
        return addressApartmentNumber;
    }

    public void setAddressApartmentNumber(int addressApartmentNumber) {
        this.addressApartmentNumber = addressApartmentNumber;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getPhoneClientId() {
        return phoneClientId;
    }

    public void setPhoneClientId(Long phoneClientId) {
        this.phoneClientId = phoneClientId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Client toClient() {
        return new Client(
                this.id,
                this.name,
                new Address(
                        this.addressId,
                        this.addressCountry,
                        this.addressCity,
                        this.addressStreet,
                        this.addressHouseNumber,
                        this.addressBuildingNumber,
                        this.addressApartmentNumber,
                        this.addressClientId),
                Set.of(new Phone(
                        this.phoneId,
                        this.phoneNumber,
                        this.phoneClientId
                )));
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

//    @Override
//    public String toString() {
//        return "ClientDto{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", addressView='" + addressView + '\'' +
//                ", phonesView='" + phonesView + '\'' +
//                '}';


    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressView='" + addressView + '\'' +
                ", phonesView='" + phonesView + '\'' +
                ", addressId=" + addressId +
                ", addressClientId=" + addressClientId +
                ", addressCountry='" + addressCountry + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", addressHouseNumber=" + addressHouseNumber +
                ", addressBuildingNumber=" + addressBuildingNumber +
                ", addressApartmentNumber=" + addressApartmentNumber +
                ", phoneId=" + phoneId +
                ", phoneClientId=" + phoneClientId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
