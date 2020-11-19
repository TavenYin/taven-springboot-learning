package com.github.taven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ATStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ATStarterApplication.class, args);
    }

}
