package ru.otus.test;

public class TestStarter {
    public static void main(String[] args) throws Exception {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.execute(Class.forName("ru.otus.test.unit.WorkingClassTest"));
    }
}
