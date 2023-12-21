package com.alja.patient.controller;

import com.alja.patient.controller_resource.PatientResource;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import com.alja.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class PatientController implements PatientResource {

    private final PatientService patientService;

    @Override
    public PatientResponseDTO registerNewPatient(PatientRegisterDTO patientRegisterDTO) {
        return patientService.registerNewPatient(patientRegisterDTO);
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @Override
    public PatientResponseDTO getPatientById(String patientId, String dataFormat) {
        return patientService.getPatientById(patientId, dataFormat);
    }

    @Override
    public PatientResponseDTO updatePatient(String patientId, PatientUpdateDTO patientUpdateDTO) {
        return patientService.updatePatient(patientId, patientUpdateDTO);
    }

    @Override
    public PatientResponseDTO deletePatientById(String patientId) {
        return patientService.deletePatient(patientId);
    }
}
