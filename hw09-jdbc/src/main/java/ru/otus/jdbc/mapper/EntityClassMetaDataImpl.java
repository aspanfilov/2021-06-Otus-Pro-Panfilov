package ru.otus.jdbc.mapper;

import ru.otus.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl implements EntityClassMetaData{
    private final Class<?> entityClazz;

    public EntityClassMetaDataImpl(String className) throws ClassNotFoundException {
        this.entityClazz = Class.forName(className);
    }

    @Override
    public String getName() {
        return entityClazz.getName();
    }

    @Override
    public Constructor getConstructor() {
        Constructor<?> constructor = entityClazz.getConstructors()[0];
        return constructor;
    }

    @Override
    public Field getIdField() {
        Field resultField = null;
        for (Field field : entityClazz.getFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                resultField = field;
                break;
            }
        }
        return resultField;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(entityClazz.getFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return (List<Field>) Arrays.stream(entityClazz.getFields()).
                filter(field -> !field.isAnnotationPresent(Id.class));
    }
}
