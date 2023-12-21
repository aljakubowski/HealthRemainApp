package com.alja.patient.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PatientResponseDetailedDTO extends PatientResponseDTO {
    private LocalDate birthDate;
    private int age;
    private String socialSecurityNumber;
    private ContactDetailsDTO contactDetailsDTO;
    private AddressDTO addressDTO;
    private LocalDateTime registrationDate;
}
