package com.alja.visit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
        info = @Info(title = "Visit Service API", description = "service for Visit management", version = "v1"))
@EnableFeignClients(basePackages = {"com.alja.patient.client", "com.alja.physician.client"})
@SpringBootApplication
public class VisitApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitApplication.class, args);
    }
}
