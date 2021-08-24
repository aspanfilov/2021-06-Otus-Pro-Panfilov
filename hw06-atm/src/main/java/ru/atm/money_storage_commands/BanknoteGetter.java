package ru.atm.money_storage_commands;

import ru.atm.Denomination;
import ru.atm.MoneyStorage;

import java.util.Map;

public class BanknoteGetter implements Command {
    @Override
    public void execute(MoneyStorage moneyStorage) {

        for (Map.Entry<Denomination, Long> getableBanknoteEntry : moneyStorage.getGetableBanknotes().entrySet()) {
            Denomination denomination = getableBanknoteEntry.getKey();
            Long calculatedBanknoteCount =
                    moneyStorage.getBanknoteCells().get(denomination) - getableBanknoteEntry.getValue();
            moneyStorage.putIntoBanknoteCells(denomination, calculatedBanknoteCount);
        }

    }
}
