package ru.otus.crm.service;

import ru.otus.crm.model.Phone;

import java.util.List;
import java.util.Optional;

public interface DBServicePhone {

    Phone savePhone(Phone phone);

    Optional<Phone> getPhone(Long id);

    List<Phone> findAll();

}
