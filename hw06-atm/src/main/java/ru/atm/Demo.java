package ru.atm;

import ru.atm.money_storage_commands.BanknoteInputter;

import java.util.HashMap;

public class Demo {
    public static void main(String[] args) {
        MoneyStorageImpl moneyStorage = new MoneyStorageImpl();
        MoneyStorageExecutor moneyStorageExecutor = new MoneyStorageExecutor(moneyStorage);

//        HashMap<Denomination, Long> banknotesToPut = new HashMap<>();
        var banknoteInputter = new BanknoteInputter(Denomination.VAL_5000, 2L);
        banknoteInputter.add(Denomination.VAL_1000, 4L);
        moneyStorageExecutor.addCommand(banknoteInputter);

        moneyStorageExecutor.executeCommands();
    }
}
