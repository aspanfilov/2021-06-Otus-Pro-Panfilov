package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.homework.DateTimeProvider;

public class ProcessorEvenSecondException implements Processor{

    private final DateTimeProvider dateTime;

    public ProcessorEvenSecondException(DateTimeProvider dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Message process(Message message) {

        if (this.dateTime.getDate().getSecond() % 2 == 0) {
            throw new RuntimeException("Even second exception");
        }

        return message;
    }

}
