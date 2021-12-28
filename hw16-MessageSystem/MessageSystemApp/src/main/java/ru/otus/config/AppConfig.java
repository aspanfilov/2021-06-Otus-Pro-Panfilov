package ru.otus.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.crm.handlers.GetClientsRequestHandler;
import ru.otus.crm.handlers.ClientResponseHandler;
import ru.otus.crm.handlers.SaveClientRequestHandler;
import ru.otus.crm.service.DBServiceClient;
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

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public GetClientsRequestHandler getClientsRequestHandler(DBServiceClient dbServiceClient) {
        return new GetClientsRequestHandler(dbServiceClient);
    }

    @Bean
    public SaveClientRequestHandler saveClientRequestHandler(DBServiceClient dbServiceClient) {
        return new SaveClientRequestHandler(dbServiceClient);
    }

    @Bean("requestHandlerDatabaseStore")
    public HandlersStore handlersStore(
            GetClientsRequestHandler getClientsRequestHandler,
            SaveClientRequestHandler saveClientRequestHandler) {

        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_CLIENTS, getClientsRequestHandler);
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_CLIENT, saveClientRequestHandler);

        return requestHandlerDatabaseStore;
    }


    @Bean
    public ClientResponseHandler clientResponseHandler() {
        return new ClientResponseHandler();
    }

    @Bean("requestHandlerFrontendStore")
    public HandlersStore handlersStore(
            ClientResponseHandler clientResponseHandler) {

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.GET_CLIENTS, clientResponseHandler);
        requestHandlerFrontendStore.addHandler(MessageType.SAVE_CLIENT, clientResponseHandler);

        return requestHandlerFrontendStore;
    }


    @Bean("databaseMessageClient")
    public MsClient databaseMsClient(
            MessageSystem messageSystem,
            @Qualifier("requestHandlerDatabaseStore") HandlersStore requestHandlerDatabaseStore) {

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, requestHandlerDatabaseStore);
        messageSystem.addClient(databaseMsClient);

        return databaseMsClient;
    }

    @Bean("frontendMessageService")
    public MsClient frontendMsClient(
            MessageSystem messageSystem,
            @Qualifier("requestHandlerFrontendStore") HandlersStore requestHandlerFrontendStore) {

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, requestHandlerFrontendStore);
        messageSystem.addClient(frontendMsClient);

        return frontendMsClient;
    }

}
