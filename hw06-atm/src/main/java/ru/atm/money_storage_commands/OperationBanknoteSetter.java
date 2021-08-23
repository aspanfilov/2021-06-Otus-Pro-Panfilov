package ru.atm.money_storage_commands;

import ru.atm.Denomination;
import ru.atm.MoneyStorage;

import java.util.SortedMap;
import java.util.TreeMap;

public class OperationBanknoteSetter implements Command{
    private final SortedMap<Denomination, Long> operationBanknotesToSet;

    public OperationBanknoteSetter(SortedMap<Denomination, Long> operationBanknotesToSet) {
        this.operationBanknotesToSet = new TreeMap<>(operationBanknotesToSet);
    }

    @Override
    public void execute(MoneyStorage moneyStorage) {
        var moneyStorageOperationBanknotes = moneyStorage.getOperationBanknotes();
        moneyStorageOperationBanknotes.clear();
        moneyStorageOperationBanknotes.putAll(this.operationBanknotesToSet);
    }
}
