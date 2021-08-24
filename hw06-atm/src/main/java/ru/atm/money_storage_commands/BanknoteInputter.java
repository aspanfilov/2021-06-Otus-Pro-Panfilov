package ru.atm.money_storage_commands;

import ru.atm.Denomination;
import ru.atm.MoneyStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BanknoteInputter implements Command{
    private final Map<Denomination, Long> banknotes = new HashMap<>();

    public BanknoteInputter(Denomination denomination, Long amount) {
        this.add(denomination, amount);
    }

    public void add(Denomination denomination, Long amount) {
        if (amount <= 0L)
            throw new IllegalArgumentException("attempt to put an amount equal to zero or less");

        Long currentAmount = Objects.requireNonNullElse(this.banknotes.get(denomination), 0L);
        this.banknotes.put(denomination, currentAmount + amount);
    }

    @Override
    public void execute(MoneyStorage moneyStorage) {
        for (Map.Entry<Denomination, Long> banknoteEntry : banknotes.entrySet()) {
            moneyStorage.putIntoBanknoteCells(banknoteEntry.getKey(), banknoteEntry.getValue());
        }
    }
}
