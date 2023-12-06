package com.alja.physician.dto;

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
    @Size(min = 3, max = 50)
    private String specializationName;
}
