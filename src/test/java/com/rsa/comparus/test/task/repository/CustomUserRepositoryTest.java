package com.rsa.comparus.test.task.repository;

import com.rsa.comparus.test.task.exception.JdbcQueryExecutionException;
import com.rsa.comparus.test.task.model.User;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class CustomUserRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres1 = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("db-1")
            .withUsername("postgres1")
            .withPassword("pass1");

    @Container
    static PostgreSQLContainer<?> postgres2 = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("db-2")
            .withUsername("postgres2")
            .withPassword("pass2");

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("db-3")
            .withUsername("mysql")
            .withPassword("root");

    @Autowired
    private CustomUserRepository userRepository;

    @Autowired
    private Map<String, NamedParameterJdbcTemplate> dataSources;

    @BeforeAll
    static void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
    }

    @Test
    void testFetchAllUsers() {
        Map<String, String> filters = new HashMap<>();

        List<User> users = userRepository.findAllUsers(filters);

        assertEquals(6, users.size());
    }

    @Test
    void testFetchAllUsersWithFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.put("username", "TestUser-01-DB-1_USERNAME");

        List<User> users = userRepository.findAllUsers(filters);

        assertEquals(1, users.size());
    }

    @Test
    @SneakyThrows
    void testExceptionWhenDBConnectionLost() {
        Map<String, String> filters = new HashMap<>();

        if (dataSources.get("db-3").getJdbcTemplate().getDataSource() instanceof HikariDataSource hikari) {
            hikari.close();
        }

        Exception ex = assertThrows(CompletionException.class, () -> userRepository.findAllUsers(filters));

        assertInstanceOf(JdbcQueryExecutionException.class, ex.getCause());
        assertNotNull(ex.getMessage());
    }
}
