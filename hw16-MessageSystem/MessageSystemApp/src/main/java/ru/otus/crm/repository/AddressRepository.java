package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Address;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findAll();

}
