package ru.otus.web.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
