package com.alja.patient.model.mapper;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsDTO;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.model.Address;
import com.alja.patient.model.ContactDetails;
import com.alja.patient.model.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class, java.time.LocalDate.class, java.util.UUID.class})
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "patientId", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "firstName", source = "patientRegisterDTO.firstName")
    @Mapping(target = "lastName", source = "patientRegisterDTO.lastName")
    @Mapping(target = "birthDate", expression = "java(LocalDate.parse(patientRegisterDTO.getBirthDate()))")
    @Mapping(target = "socialSecurityNumber", source = "patientRegisterDTO.socialSecurityNumber")
    @Mapping(target = "contactDetails", source = "patientRegisterDTO.contactDetails")
    @Mapping(target = "address", source = "patientRegisterDTO.address")
    @Mapping(target = "registrationDate", expression = "java(LocalDateTime.now())")
    PatientEntity toPatientEntity(PatientRegisterDTO patientRegisterDTO);

    @Mapping(target = "phoneNumber", source = "contactDetailsDTO.phoneNumber")
    @Mapping(target = "email", source = "contactDetailsDTO.email")
    ContactDetails toContactDetails(ContactDetailsDTO contactDetailsDTO);

    @Mapping(target = "street", source = "addressDTO.street")
    @Mapping(target = "houseNumber", source = "addressDTO.houseNumber")
    @Mapping(target = "postCode", source = "addressDTO.postCode")
    @Mapping(target = "city", source = "addressDTO.city")
    @Mapping(target = "country", source = "addressDTO.country")
    Address toAddress(AddressDTO addressDTO);

    @Mapping(target = "phoneNumber", source = "contactDetails.phoneNumber")
    @Mapping(target = "email", source = "contactDetails.email")
    ContactDetailsDTO contactDetailsToDto(ContactDetails contactDetails);

    AddressDTO addressToDto(Address address);
}
