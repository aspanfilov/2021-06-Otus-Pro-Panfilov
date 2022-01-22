package ru.otus.appcontainer;

import org.reflections.Reflections;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) throws Exception {
        orderedProcessConfig(Arrays.asList(initialConfigClasses));
    }

    public AppComponentsContainerImpl(String path) throws Exception {
        Reflections reflections = new Reflections(path);
        Set<Class<? extends Object>> configClasses = reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class);
        orderedProcessConfig(configClasses);
    }

    private void orderedProcessConfig(Collection<Class<?>> configClasses) throws Exception {
        List<?> sortedConfigClasses = configClasses.stream()
                .sorted(Comparator.comparingInt(configClass ->
                        configClass.getAnnotation(AppComponentsContainerConfig.class).order()))
                .collect(Collectors.toList());

        for (var configClass : sortedConfigClasses) {
            processConfig((Class<?>) configClass);
        }

    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);
        // You code here...

        Object configObject = configClass.getConstructor().newInstance();

        List<Method> configMethods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());

        for (Method configMethod : configMethods) {
            if (!isAppComponentNameFree(configMethod.getAnnotation(AppComponent.class).name())) {
                throw new Exception("Multiple components of the same name");
            }
            Object appComponent = configMethod.invoke(configObject, getAppComponentsByTypes(configMethod.getParameterTypes()));

            this.appComponents.add(appComponent);
            this.appComponentsByName.put(configMethod.getAnnotation(AppComponent.class).name(), appComponent);
        }
    }


    @Override
    public <C> C getAppComponent(Class<C> componentClass) throws Exception {

        C appComponent = null;

        for (Object nextComponent : appComponents) {
            if (componentClass.isAssignableFrom(nextComponent.getClass())) {
                if (appComponent == null) {
                    appComponent = (C) nextComponent;
                } else {
                    throw new Exception("Multiple components of the same type");
                }
            }
        }

        if (appComponent == null) {
            throw new Exception("Component not found");
        } else {
            return appComponent;
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) throws Exception {

        C appComponent = this.getAppComponentByName(componentName);
        if (appComponent == null) {
            throw new Exception("Component not found");
        }
        return appComponent;

    }


    private Object[] getAppComponentsByTypes(Class<?>[] parameterTypes) throws Exception {
        List<Object> appComponents = new ArrayList<>();

        for (Class<?> parameterType : parameterTypes) {
            appComponents.add(this.getAppComponent(parameterType));
        }
        return appComponents.toArray();
    }

    private <C> C getAppComponentByName(String name) {
        return (C) this.appComponentsByName.get(name);
    }
    private boolean isAppComponentNameFree(String name) {
        return this.getAppComponentByName(name) == null;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}
