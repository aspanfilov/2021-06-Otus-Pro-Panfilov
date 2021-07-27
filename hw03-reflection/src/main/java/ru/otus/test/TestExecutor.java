package ru.otus.test;

import ru.otus.test.Annotation.After;
import ru.otus.test.Annotation.Before;
import ru.otus.test.Annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestExecutor {
    private Set<Method> beforeMethods;
    private Set<Method> afterMethods;
    private HashMap<Method, String> testMethods; //key - method, value - result of executing.

    public void execute(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        beforeMethods = new HashSet<>();
        afterMethods = new HashSet<>();
        testMethods = new HashMap<>();

        fillMethods(clazz);
        executeMethods(clazz);
        printResult();
    }

    private void fillMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                this.beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                this.afterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                this.testMethods.put(method, "");
            }
        }
    }

    private void executeMethods(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<?>[] constructors = clazz.getConstructors();
        Object object = constructors[0].newInstance();

        for (Map.Entry<Method, String> testMethod : this.testMethods.entrySet()) {
            MethodTester methodTester = new MethodTester(object, testMethod.getKey(), this.beforeMethods, this.afterMethods);
            testMethods.put(testMethod.getKey(), methodTester.execute());
        }
    }

    private void printResult() {
        int countPassed = 0;
        int countFailed = 0;
        StringBuilder sbErrors = new StringBuilder();

        for (Map.Entry<Method, String> testMethod : this.testMethods.entrySet()) {
            if (testMethod.getValue().isEmpty()) {
                System.out.println(testMethod.getKey().getName() + " " + "PASSED");
                countPassed++;
            } else {
                System.out.println(testMethod.getKey().getName() + " " + "FAILED");
                countFailed++;
                sbErrors.append(testMethod.getValue()).append("\n");
            }
        }

        System.out.println("Tested methods: " + testMethods.size() +
                ", PASSED: " + countPassed + ", FAILED: " + countFailed + "\n");
        System.out.println(sbErrors.toString());
    }

}
