package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorEvenSecondException;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {

        var message = new Message.Builder(1L).build();

//        Processor processor = new ProcessorEvenSecondException(
//                LocalDateTime.of(2021, 10, 10, 10, 10, 11));
        Processor processor = new ProcessorEvenSecondException(
                LocalDateTime.of(2021, 10, 10, 10, 10, 10));

        var processors = List.of(processor);
        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        complexProcessor.handle(message);



        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
    }
}
