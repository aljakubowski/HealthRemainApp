package com.alja.physician.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class PhysicianResponseDetailedDTO extends PhysicianResponseDTO{
    private ContactDetailsDTO contactDetailsDTO;
    private AddressDTO addressDTO;
    private LocalDateTime registrationDate;
}
