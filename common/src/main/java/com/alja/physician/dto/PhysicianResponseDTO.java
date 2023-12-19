package com.alja.physician.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PhysicianResponseDTO {
    private String physicianId;
    private String firstName;
    private String lastName;
    private String physiciansSpecialization;
}
