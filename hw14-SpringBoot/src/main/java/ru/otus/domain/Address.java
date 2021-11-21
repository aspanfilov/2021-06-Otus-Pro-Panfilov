package ru.otus.domain;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private int houseNumber;

    @Column(name = "building_number")
    private int buildingNumber;

    @Column(name = "apartment_number")
    private int apartmentNumber;

    public Address() {
    }

    public Address(long id, String country, int houseNumber) {
        this.id = id;
        this.country = country;
        this.houseNumber = houseNumber;
    }

    public Address(String country, int houseNumber) {
        this.country = country;
        this.houseNumber = houseNumber;
    }

    public Address(String country,
                   String city,
                   String street,
                   int house_number,
                   int building_number,
                   int apartment_number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = house_number;
        this.buildingNumber = building_number;
        this.apartmentNumber = apartment_number;
    }

    @Override
    public Address clone() {
        var addressCloned = new Address(this.id, this.country, this.houseNumber);
        if (this.getCity() != null) {
            addressCloned.setCity(this.getCity());
        }
        if (this.getStreet() != null) {
            addressCloned.setStreet(this.getStreet());
        }
        addressCloned.setBuildingNumber(this.getBuildingNumber());
        addressCloned.setApartmentNumber(this.getApartmentNumber());
        return addressCloned;
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
