package ru.aop;

import ru.aop.annotation.Log;

public class TestLoggingImpl implements TestLogging{

    public void calculation(int param) {
        System.out.println("Calculation with 1 param");
    }

    @Log
    public void calculation(int param, int param2, int param3) {
        System.out.println("Calculation with 3 params");
    }


}
