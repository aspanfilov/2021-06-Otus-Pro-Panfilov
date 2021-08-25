package ru.atm.money_storage_commands;

import ru.atm.Denomination;
import ru.atm.MoneyStorage;

import java.util.Map;

public class GetableBanknoteCalculator implements Command {
    private final long amount;

    public GetableBanknoteCalculator(long amount) {
        this.amount = amount;
    }

    @Override
    public void execute(MoneyStorage moneyStorage) {
        if (this.amount > moneyStorage.getBalance())
            throw new IllegalArgumentException("insufficient funds");

        long currentAmount = amount;

        for (Map.Entry<Denomination, Long> banknoteCell : moneyStorage.getBanknoteCells().entrySet()) {
            Denomination denomination = banknoteCell.getKey();
            long denominationValue = moneyStorage.getDenominationValues().get(denomination);
            if (denominationValue <= currentAmount) {
                long banknoteCount = Math.min(currentAmount / denominationValue, banknoteCell.getValue());
                if (banknoteCount == 0L)
                    continue;
                moneyStorage.addIntoGetableBanknotes(denomination, banknoteCount);
                currentAmount = currentAmount - (banknoteCount * denominationValue);
            }
        }

        if (currentAmount != 0) {
            throw new IllegalArgumentException("amount is not multiple to the existing minimum denomination");
        }

    }
}
