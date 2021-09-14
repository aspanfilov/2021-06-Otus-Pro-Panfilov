package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private

    @Override
    public String getSelectAllSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(tableName);

        return sb.toString();
    }

    @Override
    public String getSelectByIdSql(String tableName, Field idField) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(tableName)
                .append(" where ").append(idField.getName())
                .append(" = ")
    }

    @Override
    public String getInsertSql() {
        return null;
    }

    @Override
    public String getUpdateSql() {
        return null;
    }
}
