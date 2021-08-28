package ru.atm;

import ru.atm.annotation.Log;

public interface TestLogging {

    @Log
    void calculation(int param);

    @Log
    void calculation(int param, int param2, int param3);
}
