package com.alja.adminpanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.alja.physician.client")
@SpringBootApplication
        (scanBasePackages = {"com.alja.adminpanel", "com.alja.physician"})
public class AdminPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminPanelApplication.class, args);
    }
}
