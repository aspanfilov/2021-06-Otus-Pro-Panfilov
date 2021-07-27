package ru.otus.test.unit;

import ru.otus.test.Annotation.After;
import ru.otus.test.Annotation.Before;
import ru.otus.test.Annotation.Test;

public class WorkingClassTest {

    public WorkingClassTest() {
    }

    @Before
    public void methodBefore() {
        System.out.println("run method Before");
    }

    @Test
    public void methodTest1() throws Exception {
        Exception e = new Exception("Ошибка ввода вывода");
        throw e;
//        System.out.println("run method Test 1");
    }

    @Test
    public void methodTest2() throws Exception {
        Exception e = new Exception("Ошибка ввода вывода");
        throw e;
//        System.out.println("run method Test 2");
    }

    @After
    public void methodAfter() {
        System.out.println("run method After");
    }

}
