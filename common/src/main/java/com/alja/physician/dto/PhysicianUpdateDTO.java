package com.alja.physician.dto;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsUpdateDTO;
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

    @Nullable
    @Size(min = 2, max = 50, message = "Physician name should have 3 to 50 characters")
    private String firstName;
    @Nullable
    @Size(min = 2, max = 50, message = "Physician last name should have 3 to 50 characters")
    private String lastName;
    @Nullable
    private String physicianSpecialization;
    @Valid
    @Nullable
    private ContactDetailsUpdateDTO contactDetails;
    @Valid
    @Nullable
    private AddressDTO address;
}
