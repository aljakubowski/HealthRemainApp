package com.alja.visit.dto;

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
    @Nullable
    private String patientId;
    @NotNull(message = "Visit status should not be empty")
    private String visitStatus;
}