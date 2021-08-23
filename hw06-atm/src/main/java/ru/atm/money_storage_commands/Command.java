package ru.atm.money_storage_commands;

import ru.atm.MoneyStorage;

@FunctionalInterface
public interface Command {
    void execute(MoneyStorage moneyStorage);
}
