package com.alja.patient.fixtures;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import com.alja.common.ContactDetailsUpdateDTO;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import com.alja.patient.model.Address;
import com.alja.patient.model.ContactDetails;
import com.alja.patient.model.PatientEntity;

import java.time.LocalDate;
import java.util.UUID;

public class PatientFixtures {

    //todo refactor class: delete not used

    public static PatientRegisterDTO createPatientRegisterDTO() {
        return PatientRegisterDTO.builder()
                .firstName("Jan")
                .lastName("Dobry")
                .contactDetails(createContactDetailsDTO())
                .address(createAddressDTO())
                .build();
    }

    public static PatientRegisterDTO createPatientRegisterDTOCustom(
            String firstName,
            String lastName,
            String birthDate,
            String socialSecurityNumber,
            String phoneNumber,
            String email,
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country) {
        return PatientRegisterDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .socialSecurityNumber(socialSecurityNumber)
                .contactDetails(createContactDetailsDTOCustom(phoneNumber, email))
                .address(createAddressDTOCustom(street, houseNumber, postCode, city, country))
                .build();
    }

    public static PatientRegisterDTO createPatientRegisterDTOCustomWithFields(
            String firstName,
            String lastName,
            String birthDate,
            String socialSecNum) {
        return PatientRegisterDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .socialSecurityNumber(socialSecNum)
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

    public static ContactDetails createContactDetailsCustom(
            String phoneNumber,
            String email) {
        return ContactDetails.builder()
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

    public static AddressDTO createAddressDTOCustom(
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

    public static PatientUpdateDTO createPatientUpdateWithFields(
            String firstName,
            String lastName,
            String birthDate,
            String socialSecNum) {
        return PatientUpdateDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .socialSecurityNumber(socialSecNum)
                .build();
    }

    public static PatientUpdateDTO createPatientUpdateWithContactDetailsUpdate(
            String phoneNumber,
            String email) {
        ContactDetailsUpdateDTO contact = ContactDetailsUpdateDTO.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        return PatientUpdateDTO.builder()
                .contactDetails(contact)
                .build();
    }
    public static PatientUpdateDTO createPatientUpdateWithAddress(
            String street,
            String houseNumber,
            String postCode,
            String city,
            String country){
        AddressDTO address = createAddressDTOCustom(
                street, houseNumber, postCode, city, country);
        return PatientUpdateDTO.builder()
                .address(address)
                .build();
    }

    public static PatientEntity createPatientWithFields(
            String firstName,
            String lastName,
            String birthDate,
            String socialSecNum) {
        return PatientEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.parse(birthDate))
                .socialSecurityNumber(socialSecNum)
                .build();
    }

    public static PatientEntity createPatientWithFieldsAndUuid(
            String firstName,
            String lastName,
            String birthDate,
            String socialSecNum,
            String uuid) {
        return PatientEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.parse(birthDate))
                .socialSecurityNumber(socialSecNum)
                .patientId(uuid)
                .build();
    }

    public static PatientEntity createPatientWithContactDetails(
            String phoneNumber,
            String email) {
        ContactDetails contact = ContactDetails.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        return PatientEntity.builder()
                .contactDetails(contact)
                .build();
    }

    public static PatientEntity createPatientWithAddress(
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
        return PatientEntity.builder()
                .address(address)
                .build();
    }

    public static PatientEntity createPatientWithAllFieldsCustom(
            String firstName,
            String lastName,
            String birthDate,
            String socialSecurityNum,
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
        return PatientEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.parse(birthDate))
                .socialSecurityNumber(socialSecurityNum)
                .patientId(uuid)
                .contactDetails(contact)
                .address(address)
                .build();
    }

    public static PatientEntity createPatientWithAllFields(){
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
        return PatientEntity.builder()
                .firstName("Jan")
                .lastName("Dobry")
                .contactDetails(contact)
                .address(address)
                .build();
    }

}
