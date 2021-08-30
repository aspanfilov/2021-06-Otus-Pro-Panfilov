package ru.aop;

import ru.aop.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

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
        private final Map<Method, Boolean> cachedLoggingUsage;

        TestLoggingInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            this.testLoggingClazz = testLogging.getClass();
            cachedLoggingUsage = new HashMap<>();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (isLoggingUsed(method)) {
                System.out.println(getMethodLog(method, args));
            }

            return method.invoke(testLogging, args);
        }

        private boolean isLoggingUsed(Method method) throws NoSuchMethodException {
            Boolean loggingUsage = this.cachedLoggingUsage.get(method);

            if (loggingUsage == null) {
                loggingUsage = isAnnotationPresent(method, Log.class);
                this.cachedLoggingUsage.put(method, loggingUsage);
            }
            return loggingUsage;
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
