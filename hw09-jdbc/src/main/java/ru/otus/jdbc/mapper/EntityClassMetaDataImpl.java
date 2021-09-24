package ru.otus.jdbc.mapper;

import ru.otus.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz){
        this.name = getName(clazz);
        this.constructor = getConstructor(clazz);
        this.idField = getIdField(clazz);
        this.allFields = getAllFields(clazz);
        this.fieldsWithoutId = getFieldsWithoutId(clazz);
    }

    private String getName(Class<T> clazz) {
        return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
    }

    private Constructor<T> getConstructor(Class<T> clazz) {
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }

    private Field getIdField(Class<T> clazz) {
        Field resultField = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                resultField = field;
                break;
            }
        }
        return resultField;
    }

    private List<Field> getAllFields(Class<T> clazz) {
        return List.of(clazz.getDeclaredFields());
    }

    private List<Field> getFieldsWithoutId(Class<T> clazz) {
        List<Field> fieldsWithoutId = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsWithoutId.add(field);
            }
        }
        return fieldsWithoutId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
