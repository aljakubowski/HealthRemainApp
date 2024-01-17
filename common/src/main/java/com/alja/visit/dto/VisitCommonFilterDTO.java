package com.alja.visit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VisitCommonFilterDTO {
    @Schema(example = "21345")
    @Nullable
    private String physicianId;
    @Schema(example = "Radiologist")
    @Nullable
    private String physicianSpecialization;
    @Schema(example = "2024-02-08T12:30:00", description = "visit date from in format: yyyy-MM-ddThh:MM:ss")
    @Nullable
    private LocalDateTime visitDateFrom;
    @Schema(example = "2024-05-08T12:30:00", description = "visit date to in format: yyyy-MM-ddThh:MM:ss")
    @Nullable
    private LocalDateTime visitDateTo;
}