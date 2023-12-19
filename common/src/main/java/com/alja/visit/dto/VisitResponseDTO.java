package com.alja.visit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class VisitResponseDTO {
    private String physicianId;
    private String firstName;
    private String lastName;
    private String physicianSpecialization;
    private String patientId;
    private LocalDateTime visitDate;
    private int durationMinutes;
    private LocalDateTime registrationDate;
    private boolean available;
}
