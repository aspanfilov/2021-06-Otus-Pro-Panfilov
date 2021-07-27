package ru.otus.test;

import java.lang.reflect.InvocationTargetException;

public class TestStarter {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.execute(Class.forName("ru.otus.test.unit.WorkingClassTest"));
    }
}
