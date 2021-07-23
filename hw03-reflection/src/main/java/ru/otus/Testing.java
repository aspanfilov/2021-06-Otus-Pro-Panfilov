package ru.otus;

import ru.otus.Annotation.After;
import ru.otus.Annotation.Before;
import ru.otus.Annotation.Test;

public class Testing {

    public Testing() {
    }

    @Before
    public void methodBefore() {
        System.out.println("run method Before");
    }

    @Test
    public void methodTest1() {
        System.out.println("run method Test 1");
    }

    @Test
    public void methodTest2() {
        System.out.println("run method Test 2");
    }

    @After
    public void methodAfter() {
        System.out.println("run method After");
    }

}
