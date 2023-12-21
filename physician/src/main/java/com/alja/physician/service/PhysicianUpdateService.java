package com.alja.physician.service;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsUpdateDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import com.alja.physician.model.Address;
import com.alja.physician.model.ContactDetails;
import com.alja.physician.model.PhysicianEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class PhysicianUpdateService {

    private final PhysicianDataValidationService physicianDataValidationService;

    public PhysicianEntity updatePhysician(PhysicianEntity entityToUpdate, PhysicianUpdateDTO physicianUpdateDTO) {
        updatePhysicianFields(entityToUpdate, physicianUpdateDTO);
        if (Objects.nonNull(physicianUpdateDTO.getContactDetails())) {
            updatePhysicianContactDetails(entityToUpdate.getContactDetails(), physicianUpdateDTO.getContactDetails());
        }
        if (Objects.nonNull(physicianUpdateDTO.getAddress())) {
            updatePhysicianAddress(entityToUpdate.getAddress(), physicianUpdateDTO.getAddress());
        }
        return entityToUpdate;
    }

    private void updatePhysicianFields(PhysicianEntity entityToUpdate, PhysicianUpdateDTO physicianUpdateDTO) {
        if (Objects.nonNull(physicianUpdateDTO.getFirstName())) {
            entityToUpdate.setFirstName(physicianUpdateDTO.getFirstName());
        }
        if (Objects.nonNull(physicianUpdateDTO.getLastName())) {
            entityToUpdate.setLastName(physicianUpdateDTO.getLastName());
        }
        if (Objects.nonNull(physicianUpdateDTO.getPhysicianSpecialization())) {
            physicianDataValidationService.validateIfSpecializationExists(physicianUpdateDTO.getPhysicianSpecialization());
            entityToUpdate.setPhysicianSpecialization(physicianUpdateDTO.getPhysicianSpecialization());
        }
    }

    private void updatePhysicianContactDetails(ContactDetails contactDetailsToUpdate, ContactDetailsUpdateDTO contactUpdate) {
        if (Objects.nonNull(contactUpdate.getPhoneNumber())
                && isDifferent(contactDetailsToUpdate.getPhoneNumber(), contactUpdate.getPhoneNumber())) {
            physicianDataValidationService.validatePhoneNumber(contactDetailsToUpdate.getPhoneNumber());
            contactDetailsToUpdate.setPhoneNumber(contactUpdate.getPhoneNumber());
        }
        if (Objects.nonNull(contactUpdate.getEmail())
                && isDifferent(contactDetailsToUpdate.getEmail(), contactUpdate.getEmail())) {
            physicianDataValidationService.validateEmail(contactUpdate.getEmail());
            contactDetailsToUpdate.setEmail(contactUpdate.getEmail());
        }
    }

    private void updatePhysicianAddress(Address addressToUpdate, AddressDTO addressUpdate) {
        if (Objects.nonNull(addressUpdate.getStreet())) {
            addressToUpdate.setStreet(addressUpdate.getStreet());
        }
        if (Objects.nonNull(addressUpdate.getHouseNumber())) {
            addressToUpdate.setHouseNumber(addressUpdate.getHouseNumber());
        }
        if (Objects.nonNull(addressUpdate.getPostCode())) {
            addressToUpdate.setPostCode(addressUpdate.getPostCode());
        }
        if (Objects.nonNull(addressUpdate.getCity())) {
            addressToUpdate.setCity(addressUpdate.getCity());
        }
        if (Objects.nonNull(addressUpdate.getCountry())) {
            addressToUpdate.setCountry(addressUpdate.getCountry());
        }
    }

    private boolean isDifferent(String existingValue, String updateValue) {
        return !existingValue.equals(updateValue);
    }

}
