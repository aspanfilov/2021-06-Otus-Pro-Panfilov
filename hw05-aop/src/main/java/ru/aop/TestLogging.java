package ru.aop;

import ru.aop.annotation.Log;

public interface TestLogging {

    void calculation(int param);

    void calculation(int param, int param2, int param3);
}
