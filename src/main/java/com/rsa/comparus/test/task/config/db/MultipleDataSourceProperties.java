package com.rsa.comparus.test.task.config.db;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Data
@Validated
@ConfigurationProperties()
public class MultipleDataSourceProperties {
    @NotNull
    public List<DataSourceProperties> dataSources;

    @Data
    public static class DataSourceProperties {
        @NotNull
        private String name;
        @NotNull
        private String url;
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private String driver;
        @NotNull
        private DbStrategy strategy;
        @NotNull
        private String table;
        private Boolean enabled;
        @NotNull
        private Map<String, String> mapping;
    }

    public enum DbStrategy {
        my_sql,
        postgresql
    }

}
