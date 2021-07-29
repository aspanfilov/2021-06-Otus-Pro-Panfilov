package ru.otus.test;

import ru.otus.test.annotation.After;
import ru.otus.test.annotation.Before;
import ru.otus.test.annotation.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestExecutor {
    private final Set<Method> beforeTestMethods = new HashSet<>();
    private final Set<Method> afterTestMethods = new HashSet<>();
    private final Set<Method> testMethods = new HashSet<>();
    private final Map<String, String> testResults = new HashMap<>(); //key - method name, value - error description

    public void execute(Class<?> clazz) throws Exception {
        fillTestMethods(clazz);
        executeTestMethods(clazz);

        System.out.println(getResultLog());
    }

    private void fillTestMethods(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                this.beforeTestMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                this.afterTestMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                this.testMethods.add(method);
            }
        }
    }

    private void executeTestMethods(Class<?> clazz) throws Exception {

        Constructor<?> constructor = clazz.getConstructor();

        for (Method testMethod : this.testMethods) {
            Object object = constructor.newInstance();
            String errorDescription = executeTestMethod(object, testMethod);
            this.testResults.put(testMethod.getName(), errorDescription);
        }
    }

    private String executeTestMethod(Object object, Method testMethod) {
        try {
            try {
                executeMethods(object, this.beforeTestMethods);
                testMethod.invoke(object);
            } finally {
                executeMethods(object, this.afterTestMethods);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
        return "";
    }

    private void executeMethods(Object object, Set<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(object);
        }
    }

    private String getResultLog() {

        int countPassed = 0;
        int countFailed = 0;
        StringBuilder sbLog = new StringBuilder();
        StringBuilder sbErrorDescription = new StringBuilder();

        for (Map.Entry<String, String> testResultEntry : this.testResults.entrySet()) {
            if (testResultEntry.getValue().isEmpty()) {
                countPassed++;
                sbLog.append(testResultEntry.getKey()).append(" ").append("PASSED").append("\n");
            } else {
                countFailed++;
                sbLog.append(testResultEntry.getKey()).append(" ").append("FAILED").append("\n");
                sbErrorDescription.append(testResultEntry.getKey()).append(":").append("\n").
                        append(testResultEntry.getValue()).append("\n");
            }
        }

        sbLog.append("\n").append("Tested methods: ").append(this.testMethods.size()).
                append(", PASSED: ").append(countPassed).
                append(", FAILED: ").append(countFailed).append("\n");

        sbLog.append("\n").append(sbErrorDescription);

        return sbLog.toString();

    }

}
