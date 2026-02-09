package com.rsa.comparus.test.task.config.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableConfigurationProperties(MultipleDataSourceProperties.class)
public class DataSourceConfig {

    @Bean
    public Map<String, NamedParameterJdbcTemplate> dataSources(MultipleDataSourceProperties properties) {
        return properties.getDataSources().stream()
                .filter(dataSourceProperties -> Boolean.TRUE.equals(dataSourceProperties.getEnabled()))
                .collect(Collectors.toMap(MultipleDataSourceProperties.DataSourceProperties::getName, this::buildDataSource));
    }

    private NamedParameterJdbcTemplate buildDataSource(MultipleDataSourceProperties.DataSourceProperties dataSourceProperties) {
        var dataSource = DataSourceBuilder.create()
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .driverClassName(dataSourceProperties.getDriver())
                .build();
        log.info("DataSource {} created", dataSourceProperties.getName());
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
