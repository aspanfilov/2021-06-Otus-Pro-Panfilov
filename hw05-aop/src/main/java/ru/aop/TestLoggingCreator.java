package ru.aop;

import ru.aop.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;

class TestLoggingCreator {

    static TestLogging create() {
        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(
                TestLoggingCreator.class.getClassLoader(),
                new Class<?>[] {TestLogging.class}, handler);
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final Class<?> testLoggingClazz;
        private final Set<Method> loggedMethods;

        TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            this.testLoggingClazz = testLogging.getClass();
            loggedMethods = new HashSet<>();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (this.loggedMethods.contains(method)) {
                System.out.println(getMethodLog(method, args));
            } else if (isAnnotationPresent(method, Log.class)) {
                this.loggedMethods.add(method);
                System.out.println(getMethodLog(method, args));
            }
            return method.invoke(testLogging, args);
        }

        private boolean isAnnotationPresent(Method method, Class<? extends Annotation> annotationClazz) throws NoSuchMethodException {
            Method methodImpl = this.testLoggingClazz.getMethod(method.getName(), method.getParameterTypes());
            return methodImpl.isAnnotationPresent(annotationClazz);
        }

        private String getMethodLog(Method method, Object[] args) {
            StringBuilder sb = new StringBuilder();
            sb.append("executed method: ").append(method.getName());

            for (var arg : args) {
                sb.append(", param: ");
                sb.append(arg.toString());
            }

            return sb.toString();
        }
    }
}
