package com.alja.physician;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
        info = @Info(title = "Physician Service API", description = "Physician data management and information provider service", version = "v1"))
@EnableFeignClients(basePackages = "com.alja.visit.client")
@SpringBootApplication
public class PhysicianApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhysicianApplication.class, args);
    }
}
