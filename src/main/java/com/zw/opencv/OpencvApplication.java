package com.zw.opencv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OpencvApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpencvApplication.class, args);
    }

}
