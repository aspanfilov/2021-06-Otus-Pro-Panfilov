package ru.otus.crm.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    List<Phone> findAll();

}
