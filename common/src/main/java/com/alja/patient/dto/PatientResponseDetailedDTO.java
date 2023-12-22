package com.alja.patient.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PatientResponseDetailedDTO extends PatientResponseDTO {
    private String birthDate;
    private int age;
    private String socialSecurityNumber;
    private ContactDetailsDTO contactDetails;
    private AddressDTO address;
    private LocalDateTime registrationDate;
}
