package com.alja.visit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VisitFilterDTO extends VisitCommonFilterDTO {
    @Schema(example = "21345")
    @Nullable
    private String patientId;
    @Schema(example = "AVAILABLE", description = "visit available statuses: AVAILABLE, RESERVED, COMPLETED, UNREALIZED")
    @NotNull(message = "Visit status should not be empty")
    private String visitStatus;
}