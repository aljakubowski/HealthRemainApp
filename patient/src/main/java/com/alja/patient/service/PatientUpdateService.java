package com.alja.patient.service;

import com.alja.common.AddressDTO;
import com.alja.common.ContactDetailsUpdateDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import com.alja.patient.model.Address;
import com.alja.patient.model.ContactDetails;
import com.alja.patient.model.PatientEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@Service
public class PatientUpdateService {

    private final PatientDataValidationService patientDataValidationService;

    public PatientEntity updatePatient(PatientEntity entityToUpdate, PatientUpdateDTO patientUpdateDTO) {
        updatePatientFields(entityToUpdate, patientUpdateDTO);
        if (Objects.nonNull(patientUpdateDTO.getContactDetails())) {
            updatePatientContactDetails(entityToUpdate.getContactDetails(), patientUpdateDTO.getContactDetails());
        }
        if (Objects.nonNull(patientUpdateDTO.getAddress())) {
            updatePatientAddress(entityToUpdate.getAddress(), patientUpdateDTO.getAddress());
        }
        return entityToUpdate;
    }

    private void updatePatientFields(PatientEntity entityToUpdate, PatientUpdateDTO patientUpdateDTO) {
        if (Objects.nonNull(patientUpdateDTO.getFirstName())
                && !entityToUpdate.getFirstName().equals(patientUpdateDTO.getFirstName())) {
            entityToUpdate.setFirstName(patientUpdateDTO.getFirstName());
        }
        if (Objects.nonNull(patientUpdateDTO.getLastName())
                && !entityToUpdate.getLastName().equals(patientUpdateDTO.getLastName())) {
            entityToUpdate.setLastName(patientUpdateDTO.getLastName());
        }
        if (Objects.nonNull(patientUpdateDTO.getBirthDate())
                && !entityToUpdate.getBirthDate().equals(LocalDate.parse(patientUpdateDTO.getBirthDate()))) {
            entityToUpdate.setBirthDate(LocalDate.parse(patientUpdateDTO.getBirthDate()));
        }
        if (Objects.nonNull(patientUpdateDTO.getSocialSecurityNumber())
                && !entityToUpdate.getSocialSecurityNumber().equals(patientUpdateDTO.getSocialSecurityNumber())) {
            patientDataValidationService.validateSocialSecurityNumber(patientUpdateDTO.getSocialSecurityNumber());
            entityToUpdate.setSocialSecurityNumber(patientUpdateDTO.getSocialSecurityNumber());
        }
    }

    private void updatePatientContactDetails(ContactDetails contactDetailsToUpdate, ContactDetailsUpdateDTO contactUpdate) {
        if (Objects.nonNull(contactUpdate.getPhoneNumber())
                && isDifferent(contactDetailsToUpdate.getPhoneNumber(), contactUpdate.getPhoneNumber())) {
            patientDataValidationService.validatePhoneNumber(contactDetailsToUpdate.getPhoneNumber());
            contactDetailsToUpdate.setPhoneNumber(contactUpdate.getPhoneNumber());
        }
        if (Objects.nonNull(contactUpdate.getEmail())
                && isDifferent(contactDetailsToUpdate.getEmail(), contactUpdate.getEmail())) {
            patientDataValidationService.validateEmail(contactUpdate.getEmail());
            contactDetailsToUpdate.setEmail(contactUpdate.getEmail());
        }
    }

    private void updatePatientAddress(Address addressToUpdate, AddressDTO addressUpdate) {
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
