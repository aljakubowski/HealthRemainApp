package com.alja.patient.service;

import com.alja.common.ContactDetailsDTO;
import com.alja.errors.PatientError;
import com.alja.exception.PatientException;
import com.alja.patient.model.PatientEntity;
import com.alja.patient.model.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@Service
public class PatientDataValidationService {

    private final PatientRepository patientRepository;
    private final static Integer ADULT_AGE = 18;

    public void validateSocialSecurityNumber(String socialSecurityNumber) {
        patientRepository.findAllBySocialSecurityNumber(
                        socialSecurityNumber).stream()
                .filter(s -> s != null && !s.isEmpty())
                .findFirst()
                .ifPresent(s -> {
                    throw new PatientException(PatientError.PATIENT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR);
                });
    }

    public PatientEntity findPatientIfPresent(String patientId) {
        return patientRepository.findPatientEntityByPatientId(patientId)
                .orElseThrow(() -> new PatientException(PatientError.PATIENT_NOT_FOUND_ERROR));
    }

    public void validateContactDetails(ContactDetailsDTO contactDetails) {
        validatePhoneNumber(contactDetails.getPhoneNumber());
        validateEmail(contactDetails.getEmail());
    }

    public void validatePhoneNumber(String phoneNumber) {
        patientRepository.findAllByContactDetailsPhoneNumber(
                        phoneNumber).stream()
                .filter(s -> s != null && !s.isEmpty())
                .findFirst()
                .ifPresent(s -> {
                    throw new PatientException(PatientError.PATIENT_PHONE_NUMBER_ALREADY_EXISTS_ERROR);
                });
    }

    public void validateEmail(String email) {
        patientRepository.findAllByContactDetailsEmail(
                        email).stream()
                .filter(s -> s != null && !s.isEmpty())
                .findFirst()
                .ifPresent(s -> {
                    throw new PatientException(PatientError.PATIENT_EMAIL_ALREADY_EXISTS_ERROR);
                });
    }

    public void validateAge(String birthDate) {
        if (calculateAge(birthDate) < ADULT_AGE) {
            throw new PatientException(PatientError.PATIENT_MINOR_ERROR);
        }
    }

    public void validateAppointedVisits(PatientEntity patientEntity) {
        if (patientEntity.getVisitsId() != null) {
            if (!patientEntity.getVisitsId().isEmpty()) {
                throw new PatientException(PatientError.PATIENT_APPOINTED_VISITS_ERROR);
            }
        }
    }

    public int calculateAge(String birthDate) {
        return Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears();
    }

}
