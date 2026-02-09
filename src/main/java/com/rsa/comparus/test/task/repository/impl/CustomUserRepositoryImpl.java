package com.rsa.comparus.test.task.repository.impl;

import com.rsa.comparus.test.task.config.db.MultipleDataSourceProperties;
import com.rsa.comparus.test.task.exception.JdbcQueryExecutionException;
import com.rsa.comparus.test.task.model.User;
import com.rsa.comparus.test.task.repository.CustomUserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    private final Map<String, NamedParameterJdbcTemplate> dataSources;
    private final MultipleDataSourceProperties properties;
    private final Executor executor;

    @Override
    public List<User> findAllUsers(Map<String, String> filters) {
        var futures = properties.getDataSources().stream()
                .map(dataSourceProperties -> CompletableFuture.supplyAsync(() -> fetchUser(dataSourceProperties, filters), executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .flatMap(future -> future.join().stream())
                .toList();
    }

    private List<User> fetchUser(MultipleDataSourceProperties.DataSourceProperties dataSourceProperties, Map<String, String> filters) {
        try {
            var jdbc = dataSources.get(dataSourceProperties.getName());
            var sql = buildSelect(dataSourceProperties);
            var params = addFiltersAndGetParams(sql, dataSourceProperties.getMapping(), filters);
            log.info("{}", sql);
            return jdbc.query(sql.toString(), params, USER_ROW_MAPPER);
        } catch (Exception ex) {
            log.error("Failed to fetch users from {}, table {}", dataSourceProperties.getName(), dataSourceProperties.getTable(), ex);
            throw new JdbcQueryExecutionException(ex);
        }
    }

    private StringBuilder buildSelect(MultipleDataSourceProperties.DataSourceProperties dataSourceProperties) {

        var selectColumns = dataSourceProperties.getMapping().entrySet().stream()
                .map(e -> e.getValue() + " AS " + e.getKey())
                .collect(Collectors.joining(", "));

        return switch (dataSourceProperties.getStrategy()) {
            case my_sql ->
                    new StringBuilder().append("SELECT ").append(selectColumns).append(" FROM ").append(dataSourceProperties.getTable());
            case postgresql ->
                    new StringBuilder().append("SELECT ").append(selectColumns).append(" FROM ").append(dataSourceProperties.getTable());
        };
    }

    private MapSqlParameterSource addFiltersAndGetParams(StringBuilder sql, Map<String, String> mapping, Map<String, String> filters) {
        var params = new MapSqlParameterSource();

        if (!filters.isEmpty()) {
            var conditions = filters.entrySet().stream()
                    .filter(entry -> !StringUtils.isEmpty(entry.getValue()) && mapping.containsKey(entry.getKey()))
                    .map(entry -> {
                        params.addValue(entry.getKey(), entry.getValue());
                        return mapping.get(entry.getKey()) + "= :" + entry.getKey();
                    })
                    .toList();

            if (!conditions.isEmpty()) {
                sql.append(" WHERE ").append(String.join(" AND ", conditions));
            }
        }

        return params;
    }
}
