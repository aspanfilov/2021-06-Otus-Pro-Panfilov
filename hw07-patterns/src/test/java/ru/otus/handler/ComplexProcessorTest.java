package ru.otus.handler;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.listener.Listener;
import ru.otus.processor.EvenSecondException;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorEvenSecondException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(message);
    }

    @Test
    @DisplayName("Процессор исключений дает исключение в четную секунду")
    void EvenSecondExceptionTest() {
        //given
        var message = new Message.Builder(1L).build();

        Processor processorWithException = new ProcessorEvenSecondException(
                LocalDateTime.of(2021, 10, 10, 10, 10, 10));

        var processors = List.of(processorWithException);

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            throw new TestException(ex.getMessage());
        });

        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

    }

    @Test
    @DisplayName("Процессор исключений не дает исключений в нечетную секунду")
    void OddSecondNoExceptionTest() {
        //given
        var message = new Message.Builder(1L).build();

        Processor processorOrdinary = new ProcessorEvenSecondException(
                LocalDateTime.of(2021, 10, 10, 10, 10, 11));

        var processors = List.of(processorOrdinary);

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            throw new TestException(ex.getMessage());
        });

        assertThatNoException().isThrownBy(() -> complexProcessor.handle(message));

    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}