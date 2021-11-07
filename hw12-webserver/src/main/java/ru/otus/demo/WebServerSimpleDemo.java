package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.web.server.ClientsWebServerSimple;
import ru.otus.web.service.TemplateProcessorImpl;

import java.util.List;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private static final Logger log = LoggerFactory.getLogger(WebServerSimpleDemo.class);

    public static void main(String[] args) throws Exception {
//        UserDao userDao = new InMemoryUserDao();
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        var templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        var adr = new Address("Russia", 1);
        var phones = List.of(new Phone("123-12-12"), new Phone("222-22-22"));

        var client = dbServiceClient.saveClient(
                new Client(
                        "dbServiceFirst",
                        adr,
                        phones));
        System.out.println(client.getAddress());
        System.out.println(client.getPhones());


        var clientsWebServer = new ClientsWebServerSimple(
                WEB_SERVER_PORT, dbServiceClient, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
