package ru.atm;

import java.util.Map;
import java.util.SortedMap;

public interface MoneyStorage {

    SortedMap<Denomination, Long> getBanknoteCells();

    void putIntoBanknoteCells(Denomination denomination, Long amount);

    SortedMap<Denomination, Long> getGetableBanknotes();

    void addIntoGetableBanknotes(Denomination denomination, Long amount);

    void clearGetableBanknotes();

    Map<Denomination, Long> getDenominationValues();

    long getTotal();

}
