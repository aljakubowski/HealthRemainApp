package com.alja.physician.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PhysicianResponseDTO{
    private String physicianId;
    private String firstName;
    private String lastName;
    private String physiciansSpecialization;
}
