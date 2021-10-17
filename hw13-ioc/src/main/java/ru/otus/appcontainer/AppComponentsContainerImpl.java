package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

//    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByTypeName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        checkConfigClass(configClass);
        // You code here...

        Object obj = configClass.getConstructor().newInstance();

        List<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());

        for (Method method : methods) {
            Object component = method.invoke(obj, getComponentsByTypes(method.getParameterTypes()));
            this.appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), component);
            this.appComponentsByTypeName.put(method.getReturnType().getSimpleName(), component);
        }
    }

    private Object[] getComponentsByTypes(Class<?>[] parameterTypes) {
        Object[] components = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            components[i] = this.appComponentsByTypeName.get(parameterTypes[i].getSimpleName());
        }

        return components;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) this.appComponentsByTypeName.get(componentClass.getSimpleName());
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) this.appComponentsByName.get(componentName);
    }
}
