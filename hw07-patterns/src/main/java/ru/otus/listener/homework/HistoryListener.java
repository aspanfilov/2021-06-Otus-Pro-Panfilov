package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final HashMap<Long, Message> messages;

    public HistoryListener() {
        this.messages = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        this.messages.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(this.messages.get(id));
    }
}
