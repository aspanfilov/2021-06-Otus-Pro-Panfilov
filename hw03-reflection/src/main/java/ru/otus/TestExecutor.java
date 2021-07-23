package ru.otus;

import ru.otus.Annotation.After;
import ru.otus.Annotation.Before;
import ru.otus.Annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

//todo сделать обработку исключений чтобы тест не прерывался в случае падения какого либо метода
//todo сделать логирование

public class TestExecutor {
    private Set<Method> BeforeMethods;
    private Set<Method> AfterMethods;
    private Set<Method> TestMethods;

    public void execute(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        BeforeMethods = new HashSet<>();
        AfterMethods = new HashSet<>();
        TestMethods = new HashSet<>();

        fillMethods(clazz);
        executeMethods(clazz);
    }

    private void fillMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                this.BeforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                this.AfterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                this.TestMethods.add(method);
            }
        }
    }

    private void executeMethods(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<?>[] constructors = clazz.getConstructors();
        Object object = constructors[0].newInstance();

        for (Method method : this.TestMethods) {
            executeMethods(object, this.BeforeMethods);
            method.invoke(object);
            executeMethods(object, this.AfterMethods);
        }
    }

    private void executeMethods(Object object, Set<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(object);
        }
    }
}
