package ru.otus.hiber.repository;

import org.springframework.stereotype.Component;

@Component
public class DataTemplateException extends RuntimeException {
    public DataTemplateException(Exception ex) {
        super(ex);
    }
}
