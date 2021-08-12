package ru.atm;

import java.util.Map;

public interface MoneyStorage {

    long getTotal();

    void put(Denomination denomination, long amount);

    Map<Denomination, Long> getOut(long amount);

}
