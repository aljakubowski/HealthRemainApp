package com.alja.physician.model.dto;

import com.alja.physician.model.PhysicianSpecialization;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PhysicianResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private PhysicianSpecialization physiciansSpecialization;
}
