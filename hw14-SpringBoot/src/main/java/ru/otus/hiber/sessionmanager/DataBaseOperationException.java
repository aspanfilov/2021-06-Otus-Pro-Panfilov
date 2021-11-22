package ru.otus.hiber.sessionmanager;

import org.springframework.stereotype.Component;

@Component
public class DataBaseOperationException extends RuntimeException {
    public DataBaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
