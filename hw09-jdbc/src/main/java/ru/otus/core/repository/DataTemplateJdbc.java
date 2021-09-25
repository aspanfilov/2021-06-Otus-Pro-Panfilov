package ru.otus.core.repository;

import ru.otus.core.annotation.Id;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQL;

    private final EntityClassMetaData<T> entityClass;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQL,
                            EntityClassMetaData<T> entityClass) {
        this.dbExecutor = dbExecutor;
        this.entitySQL = entitySQL;
        this.entityClass = entityClass;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, this.entitySQL.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createObject(rs);
                }
                return null;
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    private T createObject(ResultSet rs) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        T obj = this.entityClass.getConstructor().newInstance();

        for (Field field : this.entityClass.getAllFields()) {
            field.setAccessible(true);
            field.set(obj, rs.getObject(field.getName()));
        }

        return obj;
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, this.entitySQL.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> objList = new ArrayList<>();
            try {
                while (rs.next()) {
                    objList.add(createObject(rs));
                }
                return objList;
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T obj) {
        try {

            List<Object> fieldValues = new ArrayList<>();

            for (Field field : obj.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    fieldValues.add(field.get(obj));
                }
            }

            return dbExecutor.executeStatement(connection, this.entitySQL.getInsertSql(), fieldValues);

        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T obj) {
        try {

            List<Object> fieldValues = new ArrayList<>();
            String idValue = null;

            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    idValue = field.get(obj).toString();
                }
                field.setAccessible(true);
                fieldValues.add(field.get(obj));
            }
            fieldValues.add(idValue);

            dbExecutor.executeStatement(connection, this.entitySQL.getUpdateSql(), fieldValues);

        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
