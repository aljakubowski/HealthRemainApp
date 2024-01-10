package com.alja.visit.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private String visitStatus;
    @Nullable
    private LocalDateTime visitDate;
    @Nullable
    private List<String> physicianRecommendations;
}
