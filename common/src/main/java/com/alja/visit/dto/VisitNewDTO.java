package com.alja.visit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "21345", required = true)
    @NotNull(message = "Physician id should not be empty")
    private String physicianId;
    @Schema(example = "2024-02-08T12:30:00", required = true, description = "visit date in format: yyyy-MM-ddThh:MM:ss")
    @NotNull(message = "Visit date should not be empty")
    private LocalDateTime visitDate;
}
