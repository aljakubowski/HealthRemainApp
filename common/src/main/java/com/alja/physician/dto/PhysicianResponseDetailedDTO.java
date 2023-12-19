package com.alja.physician.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PhysicianResponseDetailedDTO extends PhysicianResponseDTO {
    private ContactDetailsDTO contactDetailsDTO;
    private AddressDTO addressDTO;
    private LocalDateTime registrationDate;
}
