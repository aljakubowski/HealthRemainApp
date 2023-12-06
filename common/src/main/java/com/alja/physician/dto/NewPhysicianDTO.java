package com.alja.physician.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewPhysicianDTO {
    @Size(min = 2, max = 50)
    private String firstName;
    @Size(min = 2, max = 50)
    private String lastName;
    @Email
    private String email;
    private String physicianSpecialization;
    @Valid
    private AddressDTO address;
}
