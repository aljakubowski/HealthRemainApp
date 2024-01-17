package com.alja.visit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "21345")
    @Nullable
    private String physicianId;
    @Schema(example = "21345")
    @Nullable
    private String patientId;
    @Schema(example = "AVAILABLE", description = "visit available statuses: AVAILABLE, RESERVED, COMPLETED, UNREALIZED")
    @Nullable
    private String visitStatus;
    @Schema(example = "2024-02-08T12:30:00", description = "visit date in format: yyyy-MM-ddThh:MM:ss")
    @Nullable
    private LocalDateTime visitDate;
    @Schema(example = "supplement with vitamins", description = "list of recommendations")
    @Nullable
    private List<String> physicianRecommendations;
}
