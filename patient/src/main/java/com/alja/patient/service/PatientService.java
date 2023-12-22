package com.alja.patient.service;

import com.alja.common.enums.PatientDataFormat;
import com.alja.patient.dto.*;
import com.alja.patient.model.PatientEntity;
import com.alja.patient.model.mapper.PatientMapper;
import com.alja.patient.model.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alja.patient.PatientLogs.*;


@Slf4j
@AllArgsConstructor
@Service
public class PatientService {

    private final PatientDataValidationService patientDataValidationService;
    private final PatientUpdateService patientUpdateService;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final LogService logService;

    // TODO: 20/12/2023 int  TESTS

    public PatientResponseDTO registerNewPatient(PatientRegisterDTO patientRegisterDTO) {
        logService.logOperation(REGISTER_NEW_PATIENT.logMessage,
                patientRegisterDTO.getFirstName(),
                patientRegisterDTO.getLastName());
        patientDataValidationService.validateSocialSecurityNumber(patientRegisterDTO.getSocialSecurityNumber());
        patientDataValidationService.validateContactDetails(patientRegisterDTO.getContactDetails());
        patientDataValidationService.validateAge(patientRegisterDTO.getBirthDate());
        PatientEntity patientEntity = patientMapper.toPatientEntity(patientRegisterDTO);
        patientRepository.save(patientEntity);
        return getPatientResponseSimple(patientEntity);
    }

    public List<PatientResponseDTO> getAllPatients() {
        logService.logOperation(GET_ALL_PATIENTS.logMessage);
        return patientRepository.findAll().stream()
                .map(this::getPatientResponseSimple)
                .collect(Collectors.toList());
    }

    public PatientResponseDTO getPatientById(String patientId, String dataFormat) {
        logService.logOperation(GET_PATIENT_BY_ID.logMessage, patientId);
        PatientEntity patientEntity = patientDataValidationService.findPatientIfPresent(patientId);
        return returnAppropriateResponse(patientEntity, dataFormat);
    }

    public PatientResponseDTO updatePatient(String PatientId, PatientUpdateDTO patientUpdateDTO) {
        //todo response depend on update ??
        logService.logOperation(UPDATE_PATIENT.logMessage, PatientId);
        PatientEntity existingPatientEntity = patientDataValidationService.findPatientIfPresent(PatientId);
        PatientEntity updatedPatientEntity
                = patientUpdateService.updatePatient(existingPatientEntity, patientUpdateDTO);
        patientRepository.save(updatedPatientEntity);
        return getPatientResponseSimple(updatedPatientEntity);
    }

    public PatientResponseDTO deletePatient(String patientId) {
        logService.logOperation(DELETE_PATIENT_BY_ID.logMessage, patientId);
        PatientEntity patientEntity = patientDataValidationService.findPatientIfPresent(patientId);
        patientRepository.deleteById(patientEntity.getId());
        return getPatientResponseSimple(patientEntity);
    }

    private PatientResponseDTO returnAppropriateResponse(PatientEntity patientEntity, String dataFormat) {
        if (dataFormat == null) {
            return getPatientResponseSimple(patientEntity);
        }
        if (dataFormat.equalsIgnoreCase(PatientDataFormat.DETAILS.name())) {
            return getPatientResponseDetailed(patientEntity);
        } else {
            return getPatientResponseWithVisits(patientEntity);
        }
    }


    private PatientResponseDTO getPatientResponseSimple(PatientEntity patientEntity) {
        return PatientResponseDTO.builder()
                .patientId(patientEntity.getPatientId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .build();
    }

    private PatientResponseDetailedDTO getPatientResponseDetailed(PatientEntity patientEntity) {
        return PatientResponseDetailedDTO.builder()
                .patientId(patientEntity.getPatientId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .birthDate(patientEntity.getBirthDate().toString())
                .age(patientDataValidationService.calculateAge(patientEntity.getBirthDate().toString()))
                .socialSecurityNumber(patientEntity.getSocialSecurityNumber())
                .contactDetails(patientMapper.contactDetailsToDto(patientEntity.getContactDetails()))
                .address(patientMapper.addressToDto(patientEntity.getAddress()))
                .registrationDate(patientEntity.getRegistrationDate())
                .build();
    }

    private PatientResponseVisitsDTO getPatientResponseWithVisits(PatientEntity patientEntity) {
        //// TODO: tbd: list of visits
        return PatientResponseVisitsDTO.builder()
                .patientId(patientEntity.getPatientId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .visitsId(List.of())
                .build();
    }

}
