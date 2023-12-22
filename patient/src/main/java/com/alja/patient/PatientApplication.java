package com.alja.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//fixme refactor
//@EnableFeignClients(basePackages = "com.alja.physician.client")
@SpringBootApplication
public class PatientApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);}

}
