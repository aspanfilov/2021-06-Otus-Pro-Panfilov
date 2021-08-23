package ru.atm.money_storage_commands;

import ru.atm.MoneyStorage;

import java.util.Objects;

public class BanknoteInputter implements Command{


    @Override
    public void execute(MoneyStorage moneyStorage) {
        if (count <= 0L) throw new IllegalArgumentException("attempt to put an amount equal to zero or less");

        Long currentCount = Objects.requireNonNullElse(this.moneyStorage.get(denomination), 0L);
        this.moneyStorage.put(denomination, currentCount + count);

        this.total = this.total + count * this.denominationValues.get(denomination);
    }
}
