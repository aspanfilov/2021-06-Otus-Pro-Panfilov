package ru.otus.crm.model;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(String name, Address address) {
        this.id = null;
        this.name = name;
        this.address = address;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(String name, Address address, List<Phone> phones) {
        this.id = null;
        this.name = name;
        this.address = address;

        phones.forEach(phone -> phone.setClient(this));
        this.phones = phones;
    }

    public Client(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public Client clone() {
        var clientCloned = new Client(this.id, this.name);

        if (this.address != null) {
            clientCloned.setAddress(this.address.clone());
        }

        if (this.phones != null) {
            var phonesCloned =
                    this.phones.stream().map(phone -> {
                        var phoneCloned = phone.clone();
                        phoneCloned.setClient(clientCloned);
                        return phoneCloned;
                    }).collect(Collectors.toList());
            clientCloned.setPhones(phonesCloned);
        }

        return clientCloned;
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

    public String getAddress() {
        return (this.address.getCountry() == null ? "" : this.address.getCountry() + ", ") +
                (this.address.getRegion() == null ? "" : this.address.getRegion() + ", ") +
                (this.address.getCity() == null ? "" : this.address.getCity() + ", ") +
                (this.address.getStreet() == null ? "" : this.address.getStreet() + ", ") +
                (this.address.getHouseNumber() == 0 ? "" : this.address.getHouseNumber()) +
                (this.address.getBuildingNumber() == 0 ? "" : "-" + this.address.getBuildingNumber()) +
                (this.address.getApartmentNumber() == 0 ? "" : "-" + this.address.getApartmentNumber());
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhones() {
        String phoneNumbers = "";
        for (int i = 0; i < this.phones.size(); i++) {
            phoneNumbers = phoneNumbers + phones.get(i).getNumber() +
                    (i == this.phones.size() - 1 ? "" : ", ");
        }
        return phoneNumbers;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
