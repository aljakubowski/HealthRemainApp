package com.alja.physician.fixtures;

import com.alja.physician.dto.AddressDTO;
import com.alja.physician.dto.ContactDetailsDTO;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.model.PhysicianSpecializationEntity;

public class PhysicianFixtures {

    public static PhysicianSpecializationEntity createSpecialization(String specializationName) {
        return PhysicianSpecializationEntity.builder()
                .specializationName(specializationName)
                .build();
    }

    public static PhysicianSpecializationDTO createSpecializationDto(String specializationName) {
        return PhysicianSpecializationDTO.builder()
                .specializationName(specializationName)
                .build();
    }

    public static PhysicianRegisterDTO createPhysicianRegisterDTO() {
        return PhysicianRegisterDTO.builder()
                .firstName("Jan")
                .lastName("Dobry")
                .physicianSpecialization("ORTHOPEDIST")
                .contactDetails(createContactDetailsDTO())
                .address(createAddressDTO())
                .build();
    }

    public static PhysicianRegisterDTO createPhysicianRegisterDTOCustom(
            String firstName,
            String lastName,
            String physicianSpecialization,
            String phoneNumber,
            String email,
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country) {
        return PhysicianRegisterDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .physicianSpecialization(physicianSpecialization)
                .contactDetails(createContactDetailsDTOCustom(phoneNumber, email))
                .address(createAddressDTOCustom(street, houseNumber, postCode, city, country))
                .build();
    }

    public static ContactDetailsDTO createContactDetailsDTO() {
        return ContactDetailsDTO.builder()
                .phoneNumber("123456789")
                .email("jand@mymail.com")
                .build();
    }

    public static ContactDetailsDTO createContactDetailsDTOCustom(
            String phoneNumber,
            String email) {
        return ContactDetailsDTO.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }

    private static AddressDTO createAddressDTO() {
        return AddressDTO.builder()
                .street("Wilcza")
                .houseNumber("12")
                .postCode("98-765")
                .city("Warsaw")
                .country("Poland")
                .build();
    }

    private static AddressDTO createAddressDTOCustom(
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country) {
        return AddressDTO.builder()
                .street(street)
                .houseNumber(houseNumber)
                .postCode(postCode)
                .city(city)
                .country(country)
                .build();
    }

}
