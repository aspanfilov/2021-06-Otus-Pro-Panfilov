package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.crm.handlers.GetClientsRequestHandler;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
public class AppConfig {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public HandlersStore requestHandlerDatabaseStore() {
        var requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_CLIENTS, new GetClientsRequestHandler(new DbServiceClientImpl()));
//        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_CLIENT, new GetUserDataRequestHandler(new DBServiceImpl()));
    }

    @Bean
    public MsClient databaseMsClient(
            MessageSystem messageSystem,
            HandlersStore requestHandlerDatabaseStore) {

        return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
    }

}
