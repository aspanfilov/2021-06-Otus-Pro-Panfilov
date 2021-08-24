package ru.atm;

import java.util.*;

public class MoneyStorageImpl implements MoneyStorage{
    private final SortedMap<Denomination, Long> banknoteCells = new TreeMap<>();
    private final SortedMap<Denomination, Long> getableBanknotes = new TreeMap<>();
    private final Map<Denomination, Long> denominationValues = new HashMap<>();
    private long total = 0;

    public MoneyStorageImpl() {
        for (Denomination denomination : Denomination.values()) {
            this.denominationValues.put(denomination, Long.parseLong(denomination.name().substring(4)));
            this.banknoteCells.put(denomination, 0L);
        }
    }

    @Override
    public SortedMap<Denomination, Long> getBanknoteCells() {
        return new TreeMap<>(this.banknoteCells);
    }
    @Override
    public void putIntoBanknoteCells(Denomination denomination, Long amount) {
        Long currentAmount = Objects.requireNonNullElse(this.banknoteCells.get(denomination), 0L);
        this.banknoteCells.put(denomination, currentAmount + amount);

        this.total += this.denominationValues.get(denomination) * amount;
    }

    @Override
    public SortedMap<Denomination, Long> getGetableBanknotes() {
        return new TreeMap<>(this.getableBanknotes);
    }

    @Override
    public Map<Denomination, Long> getDenominationValues() {
        return new HashMap<>(this.denominationValues);
    }

    @Override
    public long getTotal() {
        return this.total;
    }

}
