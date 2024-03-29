package com.alja.patient.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUpdateDTO {

    @Schema(example = "John")
    @Nullable
    @Size(min = 2, max = 50, message = "First name should have 2 to 50 characters")
    private String firstName;
    @Schema(example = "Smith")
    @Nullable
    @Size(min = 2, max = 50, message = "Last name should have 2 to 50 characters")
    private String lastName;
    @Schema(example = "1990-05-13", description = "birth date in format: yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
    private String birthDate;
    @Schema(example = "12345678910", description = "patient social security number")
    @Nullable
    @Pattern(regexp = "^\\d{11}$", message = "required format: 11 digit number")
    private String socialSecurityNumber;
    @Valid
    @Nullable
    private ContactDetailsUpdateDTO contactDetails;
    @Valid
    @Nullable
    private AddressDTO address;
}
