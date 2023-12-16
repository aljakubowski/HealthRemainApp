package com.alja.physician.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicianRegisteredResponseDTO {
    //fixme to delete?
    private String firstName;
    private String lastName;
    private String physiciansSpecialization;
    private LocalDateTime registrationDate;
}
