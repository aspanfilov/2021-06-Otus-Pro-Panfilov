package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Table("client")
public class Client {

    @Id
    @Nonnull
    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private List<Phone> phones;

    public Client(String name, Address address) {
        this(null, name, address, null);
    }

    public Client(String name, Address address, List<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceConstructor
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
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
