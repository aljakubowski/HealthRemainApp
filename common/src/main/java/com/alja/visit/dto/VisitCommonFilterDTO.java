package com.alja.visit.dto;

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
    @Nullable
    private String physicianId;
    @Nullable
    private String physicianSpecialization;
    @Nullable
    private LocalDateTime visitDateFrom;
    @Nullable
    private LocalDateTime visitDateTo;
}