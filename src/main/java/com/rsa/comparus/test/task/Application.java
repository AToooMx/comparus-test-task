package com.rsa.comparus.test.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.TimeZone;

@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
        SpringApplication.run(Application.class, args);
    }

}
