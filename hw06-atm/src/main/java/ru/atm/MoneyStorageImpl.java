package ru.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MoneyStorageImpl implements MoneyStorage{
    private final SortedMap<Denomination, Long> moneyStorage = new TreeMap<>();
    private final SortedMap<Denomination, Long> operationBanknotes = new TreeMap<>();
    private final Map<Denomination, Long> denominationValues = new HashMap<>();
    private long total = 0;

    public MoneyStorageImpl() {
        for (Denomination denomination : Denomination.values()) {
            this.denominationValues.put(denomination, Long.parseLong(denomination.name().substring(4)));
            this.moneyStorage.put(denomination, 0L);
        }
    }

    @Override
    public SortedMap<Denomination, Long> getMoneyStorage() {
        return moneyStorage;
    }

    @Override
    public SortedMap<Denomination, Long> getOperationBanknotes() {
        return operationBanknotes;
    }

    @Override
    public Map<Denomination, Long> getDenominationValues() {
        return denominationValues;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

}
