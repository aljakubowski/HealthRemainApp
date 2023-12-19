package com.alja.visit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.alja.physician.client")
@SpringBootApplication
public class VisitApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitApplication.class, args);
    }
}
