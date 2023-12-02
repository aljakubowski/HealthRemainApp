package com.alja.physician.dto;

import com.alja.enums.PhysicianSpecialization;
import lombok.Data;

@Data
public class NewPhysicianDTO {

    private String firstName;
    private String lastName;
    private String email;
    private PhysicianSpecialization physicianSpecialization;
    private AddressDTO address;
}
