package com.github.taven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ATProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ATProductApplication.class, args);
    }

}
