package ru.otus.hiber.sessionmanager;

import org.hibernate.Session;
import java.util.function.Function;

public interface TransactionAction<T> extends Function<Session, T> {
}
