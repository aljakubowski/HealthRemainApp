package com.alja.adminpanel.controller;

import com.alja.adminpanel.service.AdminPanelService;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminPanelPatientController
//        implements AdminPanelPatientResource
{
    // TODO: 21/12/2023 admin-panel - patient integration
    private final AdminPanelService adminPanelService;


//    @Override
//    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
//        List<PatientResponseDTO> patientResponseDTOs = adminPanelService.getAllPatients();
//        return new ResponseEntity<>(patientResponseDTOs, HttpStatus.OK);
//    }

//    @Override
//    public ResponseEntity<PatientResponseDTO> getPatientById(String patientId, boolean details) {
//        PatientResponseDTO patientResponseDTO = adminPanelService.getPatientById(patientId, details);
//        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
//    }


//    @Override
//    public ResponseEntity<PatientResponseDTO> deletePatientById(String patientId) {
//        PatientResponseDTO patientResponseDTO = adminPanelService.deletePatientById(patientId);
//        return new ResponseEntity<>(patientResponseDTO, HttpStatus.OK);
//    }

}