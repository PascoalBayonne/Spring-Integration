package com.pascoal.spring.batch.writer;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;

public class FieldSetSQLParameterSource implements ItemSqlParameterSourceProvider<FieldSet> {

    public SqlParameterSource createSqlParameterSource(FieldSet item) {
        final Map<String,Object> fields = new HashMap<>();
        item.getProperties().forEach((columnNameAsKey,itemValue)->fields.put(columnNameAsKey.toString(),itemValue));
        return new MapSqlParameterSource(fields);
    }
}
