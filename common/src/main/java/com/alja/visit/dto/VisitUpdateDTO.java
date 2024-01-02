package com.alja.visit.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitUpdateDTO {
    @Nullable
    private String physicianId;
    @Nullable
    private String patientId;
    @Nullable
    private LocalDateTime visitDate;
}
