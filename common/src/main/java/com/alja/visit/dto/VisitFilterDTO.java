package com.alja.visit.dto;

import com.alja.common.enums.VisitStatus;
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
public class VisitFilterDTO {
    @Nullable
    private String physicianId;
    @Nullable
    private String physicianSpecialization;
    @Nullable
    private String patientId;
    @Nullable
    private VisitStatus visitStatus;
    @Nullable
    private LocalDateTime visitDateFrom;
    @Nullable
    private LocalDateTime visitDateTo;
}
