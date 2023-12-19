package com.alja.visit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitNewDTO {
    @NotNull(message = "Physician id should not be empty")
    private String physicianId;
    private String patientId;
    @NotNull(message = "Visit date should not be empty")
    private LocalDateTime visitDate;
    @NotNull(message = "Visit duration should not be empty")
    @Size(min = 15, max = 120, message = "Visit duration should be between 15 and 120 minutes")
    private int durationMinutes;
    private LocalDateTime registrationDate;
}
