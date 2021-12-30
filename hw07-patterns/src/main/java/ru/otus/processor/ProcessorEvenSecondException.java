package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorEvenSecondException implements Processor{

    private LocalDateTime dateTime;

    public ProcessorEvenSecondException() {
    }

    public ProcessorEvenSecondException(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Message process(Message message) {

        if (this.dateTime == null) {
            this.dateTime = LocalDateTime.now();
        }

        if (this.dateTime.getSecond() % 2 == 0) {
            try {
                throw new Exception("even second exception");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        this.dateTime = null;

        return message;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
