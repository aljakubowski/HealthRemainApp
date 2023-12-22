package com.alja.physician.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PhysicianResponseDetailedDTO extends PhysicianResponseDTO {
    private ContactDetailsDTO contactDetailsDTO;
    private AddressDTO addressDTO;
    private LocalDateTime registrationDate;
}
