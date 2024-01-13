package com.alja.patient.fixtures;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import com.alja.common.ContactDetailsUpdateDTO;
import com.alja.common.enums.VisitStatus;
import com.alja.patient.dto.*;
import com.alja.patient.model.Address;
import com.alja.patient.model.ContactDetails;
import com.alja.patient.model.PatientEntity;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PatientFixtures {

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
            String country) {
        AddressDTO address = createAddressDTOCustom(
                street, houseNumber, postCode, city, country);
        return PatientUpdateDTO.builder()
                .address(address)
                .build();
    }

    public static PatientEntity createPatientWithNameAndId(
            String firstName,
            String lastName,
            String uuid) {
        return PatientEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patientId(uuid)
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
            String country) {
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
            String country) {
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

    public static PatientEntity createPatientWithVisits(List<String> visitsId) {
        return PatientEntity.builder()
                .visitsId(visitsId)
                .build();
    }

    public static PatientEntity createPatientWithFieldsAndVisits(
            String patientId,
            String firstName,
            String lastName,
            List<String> visits) {
        return PatientEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patientId(patientId)
                .visitsId(visits)
                .build();
    }

    public static VisitSimpleResponseDTO createVisitSimpleResponseDTOWithVisitId(String visitId) {
        return VisitSimpleResponseDTO.builder()
                .visitId(visitId)
                .build();
    }

    public static VisitSimpleResponseDTO createVisitSimpleResponseDTOWithAllFields(
            String visitId,
            LocalDateTime visitStartDate,
            LocalDateTime visitEndDate,
            VisitStatus visitStatus,
            String physicianId,
            String firstName,
            String lastName,
            String physicianSpecialization
    ) {
        return VisitSimpleResponseDTO.builder()
                .visitId(visitId)
                .physicianResponseDTO(createPhysicianResponseDtoWithCustomFields(physicianId,
                        firstName, lastName, physicianSpecialization))
                .visitStartDate(visitStartDate)
                .visitEndDate(visitEndDate)
                .visitStatus(visitStatus)
                .build();
    }

    public static PhysicianResponseDTO createPhysicianResponseDtoWithCustomFields(
            String id,
            String firstName,
            String lastName,
            String physicianSpecialization) {
        return PhysicianResponseDTO.builder()
                .physicianId(id)
                .firstName(firstName)
                .lastName(lastName)
                .physiciansSpecialization(physicianSpecialization)
                .build();
    }

    public static PatientResponseDTO createPatientResponseSimple(
            String patientId,
            String firstName,
            String lastName) {
        return PatientResponseDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patientId(patientId)
                .build();
    }

    public static PatientResponseVisitsDTO createPatientResponseWithVisits(
            String patientId,
            String firstName,
            String lastName,
            List<String> visits) {
        return PatientResponseVisitsDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .patientId(patientId)
                .visitsId(visits)
                .build();
    }

    public static PatientResponseDTO createPatientResponseWithAllFieldsCustom(
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
            String country) {
        return PatientResponseDetailedDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .socialSecurityNumber(socialSecurityNum)
                .patientId(uuid)
                .contactDetails(createContactDetailsDTOCustom(phoneNumber, email))
                .address(createAddressDTOCustom(street, houseNumber, postCode, city, country))
                .build();
    }

}
