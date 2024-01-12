package com.alja.patient.controller;

import com.alja.patient.controller_resource.PatientResource;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import com.alja.patient.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class PatientController implements PatientResource {
//todo int test
    private final PatientService patientService;

    @Override
    public ResponseEntity<PatientResponseDTO> registerNewPatientByPatient(PatientRegisterDTO patientRegisterDTO) {
        PatientResponseDTO patientResponseDTO = patientService.registerNewPatient(patientRegisterDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PatientResponseDTO> getPatientByIdByPatient(String patientId, String dataFormat) {
        PatientResponseDTO patientResponseDTO = patientService.getPatientById(patientId, dataFormat);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PatientResponseDTO> updatePatientByPatient(String patientId, PatientUpdateDTO patientUpdateDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(patientId, patientUpdateDTO);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PatientResponseDTO> deletePatientByIdByPatient(String patientId) {
        PatientResponseDTO patientResponseDTO = patientService.deletePatient(patientId);
        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
    }
}
