package ru.atm;

import java.util.SortedMap;

public interface ATM {
    void putBanknotes(Denomination denomination, Long count);

    SortedMap<Denomination, Long> getBanknotes(long amount);

    long getBalance();
}
