package com.alja.patient.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
public class PatientEntity {
    @Id
    private String id;
    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String socialSecurityNumber;
    private ContactDetails contactDetails;
    private Address address;
    private LocalDateTime registrationDate;
    private List<String> visitsId;
}
