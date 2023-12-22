package com.alja.patient.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import com.alja.common.annotation.FirstName;
import com.alja.common.annotation.LastName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRegisterDTO {

    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Patient birth date should not be empty")
    private String birthDate;
    @NotNull(message = "Patient social security number should not be empty")
    @Pattern(regexp = "^\\d{11}$", message = "required format: 11 digit number")
    private String socialSecurityNumber;
    @Valid
    @NotNull(message = "Patient contact details should not be empty")
    private ContactDetailsDTO contactDetails;
    @Valid
    @NotNull(message = "Patient address should not be empty")
    private AddressDTO address;
}
