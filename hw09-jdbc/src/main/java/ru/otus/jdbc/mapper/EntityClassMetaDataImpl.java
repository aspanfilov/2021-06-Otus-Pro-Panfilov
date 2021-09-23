package ru.otus.jdbc.mapper;

import ru.otus.core.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return this.clazz.getName().substring(this.clazz.getName().lastIndexOf(".") + 1);
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<T> constructor = null;
        try {
            constructor = this.clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }


    @Override
    public Field getIdField() {
        Field resultField = null;
        for (Field field : this.clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                resultField = field;
                break;
            }
        }
        return resultField;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(this.clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fieldsWithoutId = new ArrayList<>();
        for (Field field : this.clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsWithoutId.add(field);
            }
        }
        return fieldsWithoutId;
    }
}
