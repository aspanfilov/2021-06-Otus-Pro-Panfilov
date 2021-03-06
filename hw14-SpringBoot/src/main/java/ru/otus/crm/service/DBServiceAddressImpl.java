package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Address;
import ru.otus.crm.repository.AddressRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBServiceAddressImpl implements DBServiceAddress{
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final AddressRepository addressRepository;

    public DBServiceAddressImpl(TransactionManager transactionManager, AddressRepository addressRepository) {
        this.transactionManager = transactionManager;
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        return transactionManager.doInTransaction(() -> {
            var savedAddress = addressRepository.save(address);
            log.info("saved address: {}", savedAddress);
            return savedAddress;
        });
    }

    @Override
    public Optional<Address> get(Long id) {
        var addressOptional = addressRepository.findById(id);
        log.info("address: {}", addressOptional);
        return addressOptional;
    }

    @Override
    public List<Address> findAll() {
        var addressList = addressRepository.findAll();
        log.info("addressList: {}", addressList);
        return addressList;
    }


}
