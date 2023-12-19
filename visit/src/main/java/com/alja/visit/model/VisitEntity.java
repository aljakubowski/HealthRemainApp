package com.alja.visit.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
public class VisitEntity {
    @Id
    private String id;
    private String visitId;
    private String physicianId;
    private String physicianSpecialization;
    private String patientId;
    private LocalDateTime visitDate;
    private int durationMinutes;
    private LocalDateTime registrationDate;
}
