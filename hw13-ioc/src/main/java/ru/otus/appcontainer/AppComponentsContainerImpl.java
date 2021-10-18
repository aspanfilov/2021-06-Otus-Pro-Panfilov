package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

//    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
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
            this.appComponentsByName.put(component.getClass().getSimpleName(), component);
            this.appComponentsByName.put(method.getReturnType().getSimpleName(), component);

            if (!method.getReturnType().isInterface()) {
                for (Class<?> interfaceType : method.getReturnType().getInterfaces()) {
                    this.appComponentsByName.put(interfaceType.getSimpleName(), component);
                }
            }

        }
    }

    private Object[] getComponentsByTypes(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes).map(parameterType ->
                this.appComponentsByName.get(parameterType.getSimpleName()))
                .toArray();
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) this.appComponentsByName.get(componentClass.getSimpleName());
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) this.appComponentsByName.get(componentName);
    }
}
