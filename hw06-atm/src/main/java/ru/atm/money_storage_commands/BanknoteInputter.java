package ru.atm.money_storage_commands;

import ru.atm.Denomination;
import ru.atm.MoneyStorage;

import java.util.Objects;

public class BanknoteInputter implements Command{
    private Denomination denomination;
    private long amount;

    public BanknoteInputter(Denomination denomination, long amount) {
        this.denomination = denomination;
        this.amount = amount;
    }

    public BanknoteInputter setDenomination(Denomination denomination) {
        this.denomination = denomination;
        return this;
    }

    public BanknoteInputter setAmount(long amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public void execute(MoneyStorage moneyStorage) {
        if (count <= 0L) throw new IllegalArgumentException("attempt to put an amount equal to zero or less");

        Long currentCount = Objects.requireNonNullElse(this.moneyStorage.get(denomination), 0L);
        this.moneyStorage.put(denomination, currentCount + count);

        this.total = this.total + count * this.denominationValues.get(denomination);
    }
}
