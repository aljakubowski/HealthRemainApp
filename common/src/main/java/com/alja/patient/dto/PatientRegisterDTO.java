package com.alja.patient.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import com.alja.common.annotation.FirstName;
import com.alja.common.annotation.LastName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "John", required = true)
    @FirstName
    private String firstName;
    @Schema(example = "Smith", required = true)
    @LastName
    private String lastName;
    @Schema(example = "1990-05-13", required = true, description = "birth date in format: yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Patient birth date should not be empty")
    private String birthDate;
    @Schema(example = "12345678910", required = true, description = "patient social security number")
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
