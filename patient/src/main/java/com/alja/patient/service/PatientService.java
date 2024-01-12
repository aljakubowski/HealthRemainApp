package com.alja.patient.service;

import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
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
    private final PatientResponseService patientResponseService;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final LogService logService;

    public PatientResponseDTO registerNewPatient(PatientRegisterDTO patientRegisterDTO) {
        logService.logOperation(REGISTER_NEW_PATIENT.logMessage,
                patientRegisterDTO.getFirstName(),
                patientRegisterDTO.getLastName());
        patientDataValidationService.validateSocialSecurityNumber(patientRegisterDTO.getSocialSecurityNumber());
        patientDataValidationService.validateContactDetails(patientRegisterDTO.getContactDetails());
        patientDataValidationService.validateAge(patientRegisterDTO.getBirthDate());
        PatientEntity patientEntity = patientMapper.toPatientEntity(patientRegisterDTO);
        patientRepository.save(patientEntity);
        return patientResponseService.getPatientResponseSimple(patientEntity);
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
        return patientResponseService.returnAppropriateResponse(patientEntity, dataFormat);
    }

    public PatientResponseDTO updatePatient(String PatientId, PatientUpdateDTO patientUpdateDTO) {
        logService.logOperation(UPDATE_PATIENT.logMessage, PatientId);
        PatientEntity existingPatientEntity = patientDataValidationService.findPatientIfPresent(PatientId);
        PatientEntity updatedPatientEntity = patientUpdateService.updatePatient(existingPatientEntity, patientUpdateDTO);
        patientRepository.save(updatedPatientEntity);
        return patientResponseService.getPatientResponseSimple(updatedPatientEntity);
    }

    public PatientResponseDTO deletePatient(String patientId) {
        logService.logOperation(DELETE_PATIENT_BY_ID.logMessage, patientId);
        PatientEntity patientEntity = patientDataValidationService.findPatientIfPresent(patientId);
        patientDataValidationService.validateAppointedVisits(patientEntity);
        patientRepository.deleteById(patientEntity.getId());
        return patientResponseService.getPatientResponseSimple(patientEntity);
    }

    private PatientResponseDTO getPatientResponseSimple(PatientEntity patientEntity) {
        return patientResponseService.getPatientResponseSimple(patientEntity);
    }

}
