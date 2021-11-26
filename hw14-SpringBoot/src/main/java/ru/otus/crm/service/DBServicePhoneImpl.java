package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.PhoneRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

@Service
public class DBServicePhoneImpl implements DBServicePhone{

    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final PhoneRepository phoneRepository;

    public DBServicePhoneImpl(TransactionManager transactionManager, PhoneRepository phoneRepository) {
        this.transactionManager = transactionManager;
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Phone savePhone(Phone phone) {
        return transactionManager.doInTransaction(() -> {
            var savedPhone = phoneRepository.save(phone);
            log.info("saved phone: {}", savedPhone);
            return savedPhone;
        });
    }

    @Override
    public Optional<Phone> getPhone(Long id) {
        var phoneOptional = phoneRepository.findById(id);
        log.info("phone: {}", phoneOptional);
        return phoneOptional;
    }

    @Override
    public List<Phone> findAll() {
        var phoneList = phoneRepository.findAll();
        log.info("phoneList: {}", phoneList);
        return phoneList;
    }

}
