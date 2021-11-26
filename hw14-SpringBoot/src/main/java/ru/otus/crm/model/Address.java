package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("address")
public class Address {

    @Id
    private Long id;

//    private Long clientId;

    @Nonnull
    private String country;

    private String city;

    private String street;

    @Nonnull
    private int houseNumber;

    private int buildingNumber;

    private int apartmentNumber;

    public Address(String country, int houseNumber) {
        this.country = country;
        this.houseNumber = houseNumber;
    }

    @PersistenceConstructor
    public Address(Long id,
                   String country,
                   String city,
                   String street,
                   int house_number,
                   int building_number,
                   int apartment_number) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = house_number;
        this.buildingNumber = building_number;
        this.apartmentNumber = apartment_number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", buildingNumber=" + buildingNumber +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
