package com.alja.physician.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicianSpecializationDTO {

    @Schema(example = "Radiologist", required = true)
    @NotNull(message = "Physician Specialization name should not be empty")
    @Size(min = 3, max = 50, message = "Physician Specialization name should be from 3 to 50 characters")
    private String specializationName;
}
