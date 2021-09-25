package ru.otus.jdbc.mapper;

import ru.otus.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) throws NoSuchMethodException {
        this.name = getName(clazz);
        this.constructor = getConstructor(clazz);
        this.idField = getIdField(clazz);
        this.allFields = getAllFields(clazz);
        this.fieldsWithoutId = getFieldsWithoutId(clazz);
    }

    private String getName(Class<T> clazz) {
        return clazz.getSimpleName();
    }

    private Constructor<T> getConstructor(Class<T> clazz) throws NoSuchMethodException {
        return clazz.getConstructor();
    }

    private Field getIdField(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(e ->
                e.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow();

    }

    private List<Field> getAllFields(Class<T> clazz) {
        return List.of(clazz.getDeclaredFields());
    }

    private List<Field> getFieldsWithoutId(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(e ->
                !e.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
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
