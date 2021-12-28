package ru.otus.mapper;

import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClass;
    private final HwCache<String, String> cache;

    private final String selectAll = "SelectAll";
    private final String selectById = "SelectById";
    private final String insert = "Insert";
    private final String update = "Update";

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClass = entityClassMetaData;
        this.cache = new MyCache<>();
    }

    @Override
    public String getSelectAllSql() {
        return getSqlQuery(this.selectAll);
    }

    @Override
    public String getSelectByIdSql() {
        return getSqlQuery(this.selectById);
    }

    @Override
    public String getInsertSql() {
        return getSqlQuery(this.insert);
    }

    @Override
    public String getUpdateSql() {
        return getSqlQuery(this.update);
    }

    private String getSqlQuery(String queryType) {
        String sqlQuery = this.cache.get(queryType);
        if (sqlQuery == null) {
            sqlQuery = buildSqlQuery(queryType);
            this.cache.put(queryType, sqlQuery);
        }
        return sqlQuery;
    }

    private String buildSqlQuery(String queryType) {
        return switch (queryType) {
            case (selectAll) -> buildSelectAllSql();
            case (selectById) -> buildSelectByIdSql();
            case (insert) -> buildInsertSql();
            case (update) -> buildUpdateSql();
            default -> throw new IllegalArgumentException();
        };
    }

    private String buildSelectAllSql() {
        return String.format("select * from %s", this.entityClass.getName());
    }

    private String buildSelectByIdSql() {
        return String.format("select * from %s where %s = ?",
                this.entityClass.getName(),
                this.entityClass.getIdField().getName());
    }

    private String buildInsertSql() {
        StringBuilder sbQuery = new StringBuilder();
        StringBuilder sbVar = new StringBuilder();

        sbQuery.append("insert into ").append(this.entityClass.getName()).append(" (");
        for (Field field : this.entityClass.getFieldsWithoutId()) {
            sbQuery.append(field.getName()).append(", ");
            sbVar.append("?, ");
        }
        sbQuery.setLength(sbQuery.length() - 2);
        sbVar.setLength(sbVar.length() - 2);

        sbQuery.append(") values (").append(sbVar).append(")");

        return sbQuery.toString();
    }

    private String buildUpdateSql() {
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
