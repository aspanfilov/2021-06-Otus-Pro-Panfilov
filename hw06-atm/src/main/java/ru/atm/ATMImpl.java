package ru.atm;

import ru.atm.money_storage_commands.BanknoteGetter;
import ru.atm.money_storage_commands.BanknoteInputter;
import ru.atm.money_storage_commands.GetableBanknoteCalculator;

import java.util.SortedMap;

public class ATMImpl implements ATM {
    private final MoneyStorage moneyStorage;
    private final MoneyStorageExecutor moneyStorageExecutor;

    public ATMImpl(MoneyStorage moneyStorage) {
        this.moneyStorage = moneyStorage;
        this.moneyStorageExecutor = new MoneyStorageExecutor(moneyStorage);
    }

    @Override
    public void putBanknotes(Denomination denomination, Long count) {
        var banknoteInputter = new BanknoteInputter(denomination, count);
        moneyStorageExecutor.addCommand(banknoteInputter);
        moneyStorageExecutor.executeCommands();
    }

    @Override
    public SortedMap<Denomination, Long> getBanknotes(long amount) {
        moneyStorageExecutor.addCommand(new GetableBanknoteCalculator(amount));
        moneyStorageExecutor.addCommand(new BanknoteGetter());
        moneyStorageExecutor.executeCommands();
        var getableBanknotes = this.moneyStorage.getGetableBanknotes();
        this.moneyStorage.clearGetableBanknotes();
        return getableBanknotes;
    }

    @Override
    public long getBalance() {
        return moneyStorage.getBalance();
    }

}
