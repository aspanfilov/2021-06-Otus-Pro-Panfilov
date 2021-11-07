package ru.otus.web.server;

public interface ClientsWebServer {
    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
