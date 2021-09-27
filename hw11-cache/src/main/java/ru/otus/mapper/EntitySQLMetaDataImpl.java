package ru.otus.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData{
    private final EntityClassMetaData<T> entityClass;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClass = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", this.entityClass.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s = ?",
                this.entityClass.getName(),
                this.entityClass.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        StringBuilder sbQuery = new StringBuilder();
        StringBuilder sbVar = new StringBuilder();

        sbQuery.append("insert into ").append(this.entityClass.getName()).append(" (");
        for (Field field : this.entityClass.getFieldsWithoutId()) {
            sbQuery.append(field.getName()).append(", ");
            sbVar.append("?, ");
        }
        sbQuery.setLength(sbQuery.length()-2);
        sbVar.setLength(sbVar.length()-2);

        sbQuery.append(") values (").append(sbVar).append(")");

        return sbQuery.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder sbQuery = new StringBuilder();

        sbQuery.append("update ").append(this.entityClass.getName()).append(" set ");
        for (Field field : this.entityClass.getFieldsWithoutId()) {
            sbQuery.append(field.getName()).append(" = ?, ");
        }
        sbQuery.setLength(sbQuery.length() - 2);

        sbQuery.append(" where ").append(this.entityClass.getIdField().getName())
                .append(" = ?");

        return sbQuery.toString();
    }
}
