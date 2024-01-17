package com.alja.physician.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicianUpdateDTO {

    @Schema(example = "Mark")
    @Nullable
    @Size(min = 2, max = 50, message = "Physician name should have 3 to 50 characters")
    private String firstName;
    @Schema(example = "Johnson")
    @Nullable
    @Size(min = 2, max = 50, message = "Physician last name should have 3 to 50 characters")
    private String lastName;
    @Schema(example = "Radiologist", description = "Physician specialization")
    @Nullable
    private String physicianSpecialization;
    @Valid
    @Nullable
    private ContactDetailsUpdateDTO contactDetails;
    @Valid
    @Nullable
    private AddressDTO address;
}
