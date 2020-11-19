package com.github.taven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class ATOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ATOrderApplication.class, args);
    }

}
