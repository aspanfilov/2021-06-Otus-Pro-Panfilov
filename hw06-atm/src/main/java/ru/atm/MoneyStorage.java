package ru.atm;

import java.util.Map;
import java.util.SortedMap;

public interface MoneyStorage {

    SortedMap<Denomination, Long> getMoneyStorage();

    SortedMap<Denomination, Long> getGetableBanknotes();

    Map<Denomination, Long> getDenominationValues();

    long getTotal();

    void setTotal(long total);

}
