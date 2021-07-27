package ru.otus.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class MethodTester {
    private final Object object;
    private final Method method;
    private final Set<Method> beforeMethods;
    private final Set<Method> afterMethods;


    public MethodTester(Object object, Method method, Set<Method> beforeMethods, Set<Method> afterMethods) {
        this.object = object;
        this.method = method;
        this.beforeMethods = beforeMethods;
        this.afterMethods = afterMethods;
    }

    public String execute() throws InvocationTargetException, IllegalAccessException {
        try {
            try {
                executeMethods(this.beforeMethods);
                this.method.invoke(this.object);
            } finally {
                executeMethods(this.afterMethods);
            }
        } catch (Exception e) {
//            String errorDescription = method.getName() + ": \n"
//                    + e.getStackTrace()..toString();

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            return method.getName() + ": \n" + sw.toString();
        }
        return "";
    }

    private void executeMethods(Set<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(this.object);
        }
    }

}
