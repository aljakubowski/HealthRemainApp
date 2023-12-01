package com.alja.physician.model.dto;

import com.alja.physician.model.PhysicianSpecialization;
import lombok.Data;

@Data
public class NewPhysicianDTO {

    private String firstName;
    private String lastName;
    private String email;
    private PhysicianSpecialization physicianSpecialization;
    private AddressDTO address;
}
