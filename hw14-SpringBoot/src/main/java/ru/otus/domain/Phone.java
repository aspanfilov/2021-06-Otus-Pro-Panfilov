package ru.otus.domain;

import ru.otus.crm.model.Client;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    public Phone(String number, Client client) {
        this.number = number;
        this.client = client;
    }

    public Phone(long id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }

    public Phone clone() {
        return new Phone(this.id, this.number, this.client);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
