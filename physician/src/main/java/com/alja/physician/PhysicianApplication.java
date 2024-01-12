package com.alja.physician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.alja.visit.client")
@SpringBootApplication
public class PhysicianApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhysicianApplication.class, args);
    }
}
