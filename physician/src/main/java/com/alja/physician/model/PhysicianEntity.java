package com.alja.physician.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
public class PhysicianEntity {
    @Id
    private String id;
    private String physicianId;
    private String firstName;
    private String lastName;
    private String physicianSpecialization;
    private ContactDetails contactDetails;
    private Address address;
    private LocalDateTime registrationDate;
}
