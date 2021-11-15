package ru.otus.protobuf;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.RealService;
import ru.otus.protobuf.service.RealServiceImpl;
import ru.otus.protobuf.service.RemoteServiceImpl;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8191;

    public static void main(String[] args) throws IOException, InterruptedException {
        RealService realService = new RealServiceImpl();
        RemoteServiceImpl remoteService = new RemoteServiceImpl(realService);

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteService)
                .build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
