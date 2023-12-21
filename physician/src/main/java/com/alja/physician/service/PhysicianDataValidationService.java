package com.alja.physician.service;

import com.alja.errors.PhysicianError;
import com.alja.exception.PhysicianException;
import com.alja.common.ContactDetailsDTO;
import com.alja.physician.model.PhysicianEntity;
import com.alja.physician.model.repository.PhysicianRepository;
import com.alja.physician.model.repository.PhysicianSpecializationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PhysicianDataValidationService {

    private final PhysicianRepository physicianRepository;
    private final PhysicianSpecializationRepository physicianSpecializationRepository;

    public PhysicianEntity findPhysicianIfPresent(String physicianId){
        return physicianRepository.findPhysicianEntityByPhysicianId(physicianId)
                .orElseThrow(() -> new PhysicianException(PhysicianError.PHYSICIAN_NOT_FOUND_ERROR));
    }

    public void validateIfSpecializationExists(String specialization) {
        if (!physicianSpecializationRepository.existsBySpecializationName(specialization)) {
            throw new PhysicianException(PhysicianError.PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR);
        }
    }

    public void validateIfSpecializationAlreadyExists(String specializationName) {
        if (physicianSpecializationRepository.existsBySpecializationName(specializationName)) {
            throw new PhysicianException(PhysicianError.PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR);
        }
    }

    public void validateContactDetails(ContactDetailsDTO contactDetails) {
        validatePhoneNumber(contactDetails.getPhoneNumber());
        validateEmail(contactDetails.getEmail());
    }

    public void validatePhoneNumber(String phoneNumber) {
        physicianRepository.findAllByContactDetailsPhoneNumber(
                        phoneNumber).stream()
                .filter(s -> s != null && !s.isEmpty())
                .findFirst()
                .ifPresent(s -> {
                    throw new PhysicianException(PhysicianError.PHYSICIAN_PHONE_NUMBER_ALREADY_EXISTS_ERROR);
                });
    }

    public void validateEmail(String email) {
        physicianRepository.findAllByContactDetailsEmail(
                        email).stream()
                .filter(s -> s != null && !s.isEmpty())
                .findFirst()
                .ifPresent(s -> {
                    throw new PhysicianException(PhysicianError.PHYSICIAN_EMAIL_ALREADY_EXISTS_ERROR);
                });
    }
}
