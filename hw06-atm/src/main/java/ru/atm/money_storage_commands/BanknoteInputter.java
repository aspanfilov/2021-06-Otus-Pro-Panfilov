package ru.atm.money_storage_commands;

import ru.atm.Denomination;
import ru.atm.MoneyStorage;

import java.util.*;

public class BanknoteInputter implements Command {
    private final Map<Denomination, Long> banknotes;

    public BanknoteInputter(Denomination denomination, Long amount) {
        this.banknotes = new TreeMap<>();
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
            Denomination denomination = banknoteEntry.getKey();
            Long calculatedBanknoteCount =
                    moneyStorage.getBanknoteCells().get(denomination) + banknoteEntry.getValue();
            moneyStorage.putIntoBanknoteCells(denomination, calculatedBanknoteCount);
        }
    }
}
