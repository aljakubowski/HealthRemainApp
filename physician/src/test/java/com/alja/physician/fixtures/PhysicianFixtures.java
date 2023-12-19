package com.alja.physician.fixtures;

import com.alja.physician.dto.*;
import com.alja.physician.model.Address;
import com.alja.physician.model.ContactDetails;
import com.alja.physician.model.PhysicianEntity;
import com.alja.physician.model.PhysicianSpecializationEntity;

import java.util.UUID;

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

    public static PhysicianRegisterDTO createPhysicianRegisterDTOCustomWithFields(
            String firstName,
            String lastName,
            String physicianSpecialization) {
        return PhysicianRegisterDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .physicianSpecialization(physicianSpecialization)
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

    public static PhysicianUpdateDTO createPhysicianUpdateWithFields(
            String firstName,
            String lastName,
            String physicianSpecialization) {
        return PhysicianUpdateDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .physicianSpecialization(physicianSpecialization)
                .build();
    }

    public static PhysicianUpdateDTO createPhysicianUpdateWithContactDetailsUpdate(
            String phoneNumber,
            String email) {
        ContactDetailsUpdateDTO contact = ContactDetailsUpdateDTO.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        return PhysicianUpdateDTO.builder()
                .contactDetails(contact)
                .build();
    }
    public static PhysicianUpdateDTO createPhysicianUpdateWithAddress(
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country){
        AddressDTO address = createAddressDTOCustom(
                street, houseNumber, postCode, city, country);
        return PhysicianUpdateDTO.builder()
                .address(address)
                .build();
    }

    public static PhysicianEntity createPhysicianWithFields(
            String firstName,
            String lastName,
            String physicianSpecialization) {
        return PhysicianEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .physicianSpecialization(physicianSpecialization)
                .build();
    }

    public static PhysicianEntity createPhysicianWithFieldsAndUuid(
            String firstName,
            String lastName,
            String physicianSpecialization,
            String uuid) {
        return PhysicianEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .physicianSpecialization(physicianSpecialization)
                .physicianId(uuid)
                .build();
    }

    public static PhysicianEntity createPhysicianWithContactDetails(
            String phoneNumber,
            String email) {
        ContactDetails contact = ContactDetails.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        return PhysicianEntity.builder()
                .contactDetails(contact)
                .build();
    }

    public static PhysicianEntity createPhysicianWithAddress(
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country){
        Address address = Address.builder()
                .street(street)
                .houseNumber(houseNumber)
                .postCode(postCode)
                .city(city)
                .country(country)
                .build();
        return PhysicianEntity.builder()
                .address(address)
                .build();
    }

    public static PhysicianEntity createPhysicianWithAllFieldsCustom(
            String firstName,
            String lastName,
            String physicianSpecialization,
            String uuid,
            String phoneNumber,
            String email,
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country){
        ContactDetails contact = ContactDetails.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        Address address = Address.builder()
                .street(street)
                .houseNumber(houseNumber)
                .postCode(postCode)
                .city(city)
                .country(country)
                .build();
        return PhysicianEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .physicianSpecialization(physicianSpecialization)
                .physicianId(uuid)
                .contactDetails(contact)
                .address(address)
                .build();
    }

    public static PhysicianEntity createPhysicianWithAllFields(){
        ContactDetails contact = ContactDetails.builder()
                .phoneNumber("123456789")
                .email("mail@mail.com")
                .build();
        Address address = Address.builder()
                .street("street")
                .houseNumber("houseNumber")
                .postCode("postCode")
                .city("city")
                .country("country")
                .build();
        return PhysicianEntity.builder()
                .firstName("Jan")
                .lastName("Dobry")
                .physicianSpecialization("ORTHOPEDIST")
                .contactDetails(contact)
                .address(address)
                .build();
    }

}
