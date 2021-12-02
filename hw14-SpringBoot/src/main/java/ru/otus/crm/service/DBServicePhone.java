package ru.otus.crm.service;

import ru.otus.crm.model.Phone;

import java.util.List;
import java.util.Optional;

public interface DBServicePhone {

    Phone save(Phone phone);

    Optional<Phone> get(Long id);

    List<Phone> findAll();

}
