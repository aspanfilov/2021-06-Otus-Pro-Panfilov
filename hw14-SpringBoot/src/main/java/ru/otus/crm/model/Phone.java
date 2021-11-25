package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("phone")
public class Phone{

    @Id
    private long id;

    @Nonnull
    private String number;

    @Nonnull
    private final int clientId;

    public Phone(String number) {
        this.number = number;
        this.clientId = 0;
    }

    @PersistenceConstructor
    public Phone(long id, String number, int clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
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

    public int getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
