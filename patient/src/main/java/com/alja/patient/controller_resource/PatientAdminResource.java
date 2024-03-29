package com.alja.patient.controller_resource;

import com.alja.common.annotation.PatientData;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.patient.controller_resource.PatientAdminResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PatientAdminResource {

    String RESOURCE_PATH = "/api/v1/patient/admin";
    String PATIENT_ID_PATH = "/{patientId}";
    String PATIENT_DATA_FORMAT = "/dataFormat";

    @PostMapping
    ResponseEntity<PatientResponseDTO> registerNewPatient(@Valid @RequestBody PatientRegisterDTO patientRegisterDTO);

    @GetMapping
    ResponseEntity<List<PatientResponseDTO>> getAllPatients();

    @GetMapping(PATIENT_ID_PATH + PATIENT_DATA_FORMAT)
    ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("patientId") String patientId,
                                                      @RequestParam(name = "dataFormat", required = false) @PatientData String dataFormat);

    @PutMapping(PATIENT_ID_PATH)
    ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable String patientId,
                                                     @Valid @RequestBody PatientUpdateDTO patientUpdateDTO);

    @DeleteMapping(PATIENT_ID_PATH)
    ResponseEntity<PatientResponseDTO> deletePatientById(@PathVariable String patientId);

}
