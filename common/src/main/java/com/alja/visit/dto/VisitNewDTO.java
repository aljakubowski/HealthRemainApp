package com.alja.visit.dto;

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
public class VisitNewDTO {
    @NotNull(message = "Physician id should not be empty")
    private String physicianId;
    @NotNull(message = "Visit date should not be empty")
    private LocalDateTime visitDate;
}
