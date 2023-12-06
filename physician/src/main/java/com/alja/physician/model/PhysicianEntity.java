package com.alja.physician.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Document
public class PhysicianEntity {

    @Id
    private String id;
    private UUID employeeId;//String?
    private String firstName;
    private String lastName;
    private String physicianSpecialization;
    private String phoneNumber;
    private String email;
    private Address address;
    private LocalDateTime registrationDate;
}
