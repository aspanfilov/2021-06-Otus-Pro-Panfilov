package ru.otus.crm.service;

import ru.otus.crm.model.Address;

import java.util.List;
import java.util.Optional;

public interface DBServiceAddress {

    Address save(Address address);

    Optional<Address> get(Long id);

    List<Address> findAll();

}
