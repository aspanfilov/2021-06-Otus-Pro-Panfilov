package ru.atm;

public class Demo {

    public static void main(String[] args) {
        TestLogging testLogging = TestLoggingCreator.create();
        testLogging.calculation(34);
        testLogging.calculation(1);
        testLogging.calculation(123, 0, 10);
    }

}
