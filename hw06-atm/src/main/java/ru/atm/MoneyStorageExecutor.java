package ru.atm;

import ru.atm.money_storage_commands.Command;

import java.util.ArrayDeque;
import java.util.Queue;

public class MoneyStorageExecutor {
    private final MoneyStorage moneyStorage;
    private final Queue<Command> commands = new ArrayDeque<>();

    public MoneyStorageExecutor(MoneyStorage moneyStorage) {
        this.moneyStorage = moneyStorage;
    }

    void addCommand(Command command) {
        this.commands.add(command);
    }

    void executeCommands() {
        Command command;
        while ((command = commands.poll()) != null) {
            command.execute(moneyStorage);
        }
    }

}
