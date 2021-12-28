package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplateJdbc;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DBServiceClientCachedImpl;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.DbServiceManagerImpl;
import ru.otus.mapper.EntityClassMetaData;
import ru.otus.mapper.EntityClassMetaDataImpl;
import ru.otus.mapper.EntitySQLMetaData;
import ru.otus.mapper.EntitySQLMetaDataImpl;

import javax.sql.DataSource;
import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) throws NoSuchMethodException {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        EntityClassMetaData<Client> entityClassMetaDataClient  = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(
                dbExecutor,
                entitySQLMetaDataClient,
                entityClassMetaDataClient); //реализация DataTemplate, универсальная

        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        var dbServiceClientCached = new DBServiceClientCachedImpl(dbServiceClient);

        var testExecTimeOriginal = getTestExecutionTime(dbServiceClient, 10);
        var testExecTimeCached = getTestExecutionTime(dbServiceClientCached, 10);

        log.info("Время выполнения запроса к базе: {}", testExecTimeOriginal);
        log.info("Время выполнения запроса к кэшу: {}", testExecTimeCached);
        log.info("Разница: {}", testExecTimeOriginal-testExecTimeCached);

    }

    private static long getTestExecutionTime(DBServiceClient dbServiceClient, int count) {

        var clientFirst = dbServiceClient.saveClient(new Client("dbServiceFirst"));
        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));

        var startTime = new Date();
        for (int i = 0; i < count; i++) {
            var foundClientFirst = dbServiceClient.getClient(clientFirst.getId());
            var foundClientSecond = dbServiceClient.getClient(clientSecond.getId());
        }
        var finishTime = new Date();

        return finishTime.getTime() - startTime.getTime();
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
