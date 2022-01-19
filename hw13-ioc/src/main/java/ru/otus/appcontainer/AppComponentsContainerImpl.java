package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<String, Object> appComponentsByClass = new HashMap<>();
    private final Map<String, Object> appComponentsByInterface = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) throws Exception {
        List<?> listConfigClasses = Arrays.stream(initialConfigClasses)
                .sorted(Comparator.comparingInt(configClass ->
                        configClass.getAnnotation(AppComponentsContainerConfig.class).order()))
                .collect(Collectors.toList());

        for (var configClass : listConfigClasses) {
            processConfig((Class<?>) configClass);
        }
    }

    public AppComponentsContainerImpl(String path) throws Exception {
        Reflections reflections = new Reflections(path);
        Set<Class<? extends Object>> configClasses = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);

        List<?> listConfigClasses = configClasses.stream()
                .sorted(Comparator.comparingInt(configClass ->
                        configClass.getAnnotation(AppComponentsContainerConfig.class).order()))
                .collect(Collectors.toList());

        for (var configClass : listConfigClasses) {
            processConfig((Class<?>) configClass);
        }
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);
        // You code here...

        Object configObject = configClass.getConstructor().newInstance();

        List<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());

        for (Method method : methods) {
            Object component = method.invoke(configObject, getComponentsByTypes(method.getParameterTypes()));

            if (typeComponentExists(component)) {
                throw new Exception("Multiple components of the same type");
            }

            this.appComponents.add(component);
            this.appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), component);
            this.appComponentsByClass.put(component.getClass().getSimpleName(), component);
            if (method.getReturnType().isInterface()) {
                this.appComponentsByInterface.put(method.getReturnType().getSimpleName(), component);
            }


        }
    }

    private boolean typeComponentExists(Object component) {
        for (Object appComponent : appComponents) {
            if (appComponent.getClass().isAssignableFrom(component.getClass())) {
                return true;
            }
        }
        return false;
    }

    private Object[] getComponentsByTypes(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(parameterType ->
                        this.getAppComponent(parameterType.getSimpleName()))
                .toArray();
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        String className = componentClass.getSimpleName();

        C appComponent = (C) this.appComponentsByClass.get(className);

        if (appComponent == null) {
            appComponent = (C) this.appComponentsByInterface.get(className);
        }

        return appComponent;
    }

    @Override
    public <C> C getAppComponent(String componentName) {

        C appComponent = (C) this.appComponentsByClass.get(componentName);

        if (appComponent == null) {
            appComponent = (C) this.appComponentsByInterface.get(componentName);
        }
        if (appComponent == null) {
            appComponent = (C) this.appComponentsByName.get(componentName);
        }

        return appComponent;
    }
}
