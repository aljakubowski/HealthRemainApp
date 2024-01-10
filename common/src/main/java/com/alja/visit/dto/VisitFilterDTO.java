package com.alja.visit.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitFilterDTO {
    @Nullable
    private String physicianId;
    @Nullable
    private String physicianSpecialization;
    @Nullable
    private String patientId;
    @NotNull(message = "Visit status should not be empty")
    private String visitStatus;
    @Nullable
    private LocalDateTime visitDateFrom;
    @Nullable
    private LocalDateTime visitDateTo;
}