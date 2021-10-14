package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        checkConfigClass(configClass);
        // You code here...
        var obj = configClass.getConstructors()[0].newInstance();

        Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    var a = method.getParameterTypes();
                    var b = method.getTypeParameters();
//                    method.invoke(obj, getAppComponents);
                    System.out.println(method.getName());
                    //По классам (типам) параметров метода найти соответствующие другие методы
                    //  и взять у них имя из аннотации, чтобы по имени получить объект
                    method.getParameterTypes()[0].getSimpleName(); //Массив классов параметров передать в процедуру и получить массив объектов значений из мапы
                    System.out.println(method.getParameters());
                    getAppComponents(method.getParameters());
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object[] getAppComponents(Parameter[] parameters) {

        Object[] result = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            result[i] = this.appComponentsByName.get(parameters[i].getName());
        }
        return result;

    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return null;
    }
}
