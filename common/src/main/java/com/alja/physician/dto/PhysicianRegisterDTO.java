package com.alja.physician.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicianRegisterDTO {

    @Schema(example = "Mark", required = true)
    @NotNull(message = "Physician name should not be empty")
    @Size(min = 2, max = 50, message = "Physician name should have 3 to 50 characters")
    private String firstName;
    @Schema(example = "Johnson", required = true)
    @NotNull(message = "Physician last name should not be empty")
    @Size(min = 2, max = 50, message = "Physician last name should have 3 to 50 characters")
    private String lastName;
    @Schema(example = "Radiologist", required = true, description = "Physician specialization")
    @NotNull(message = "Physician Specialization name should not be empty")
    private String physicianSpecialization;
    @Valid
    @NotNull(message = "Physician contact details should not be empty")
    private ContactDetailsDTO contactDetails;
    @Valid
    @NotNull(message = "Physician address should not be empty")
    private AddressDTO address;
}
