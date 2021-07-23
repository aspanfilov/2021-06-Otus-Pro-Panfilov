package ru.otus;

import java.lang.reflect.InvocationTargetException;

public class Application {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.execute(Class.forName("ru.otus.Testing"));
    }
}
