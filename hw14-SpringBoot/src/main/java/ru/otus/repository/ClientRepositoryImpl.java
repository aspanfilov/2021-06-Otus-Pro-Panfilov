package ru.otus.repository;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Address;
import ru.otus.domain.Client;
import ru.otus.domain.Phone;
import ru.otus.hiber.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.hiber.repository.DataTemplate;
import ru.otus.hiber.repository.DataTemplateHibernate;
import ru.otus.hiber.repository.HibernateUtils;
import ru.otus.hiber.sessionmanager.TransactionManager;
import ru.otus.hiber.sessionmanager.TransactionManagerHibernate;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository{

    //hibernate
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final Logger log = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

//    public ClientRepositoryImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {

    public ClientRepositoryImpl() {
        //++hibernate
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        //++hibernate

        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientTemplate;

    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
