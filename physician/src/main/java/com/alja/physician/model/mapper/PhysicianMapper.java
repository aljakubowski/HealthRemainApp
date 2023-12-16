package com.alja.physician.model.mapper;

import com.alja.physician.dto.*;
import com.alja.physician.model.Address;
import com.alja.physician.model.ContactDetails;
import com.alja.physician.model.PhysicianEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class, java.util.UUID.class})
public interface PhysicianMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "physicianId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "physicianSpecialization", source = "physicianRegisterDTO.physicianSpecialization")
    @Mapping(target = "firstName", source = "physicianRegisterDTO.firstName")
    @Mapping(target = "lastName", source = "physicianRegisterDTO.lastName")
    @Mapping(target = "contactDetails", source = "physicianRegisterDTO.contactDetails")
    @Mapping(target = "address", source = "physicianRegisterDTO.address")
    PhysicianEntity toPhysicianEntity(PhysicianRegisterDTO physicianRegisterDTO);

    @Mapping(target = "phoneNumber", source = "contactDetailsDTO.phoneNumber")
    @Mapping(target = "email", source = "contactDetailsDTO.email")
    ContactDetails toContactDetails(ContactDetailsDTO contactDetailsDTO);

    @Mapping(target = "street", source = "addressDTO.street")
    @Mapping(target = "houseNumber", source = "addressDTO.houseNumber")
    @Mapping(target = "postCode", source = "addressDTO.postCode")
    @Mapping(target = "city", source = "addressDTO.city")
    @Mapping(target = "country", source = "addressDTO.country")
    Address toAddress(AddressDTO addressDTO);

    ContactDetailsDTO contactDetailsToDto(ContactDetails contactDetails);

    AddressDTO addressToDto(Address address);
}
