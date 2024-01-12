package com.alja.patient.controller_resource;

import com.alja.common.annotation.PatientData;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.alja.patient.controller_resource.PatientResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PatientResource {

    String RESOURCE_PATH = "/api/v1/patient";
    String PATIENT_ID_PATH = "/{patientId}";
    String PATIENT_DATA_FORMAT = "/dataFormat";

    @PostMapping
    ResponseEntity<PatientResponseDTO> registerNewPatientByPatient(@Valid @RequestBody PatientRegisterDTO patientRegisterDTODTO);

    @GetMapping(PATIENT_ID_PATH + PATIENT_DATA_FORMAT)
    ResponseEntity<PatientResponseDTO> getPatientByIdByPatient(@PathVariable("patientId") String patientId,
                                                               @RequestParam(name = "dataFormat", required = false) @PatientData String dataFormat);

    @PutMapping(PATIENT_ID_PATH)
    ResponseEntity<PatientResponseDTO> updatePatientByPatient(@PathVariable String patientId,
                                                              @Valid @RequestBody PatientUpdateDTO patientUpdateDTO);

    @DeleteMapping(PATIENT_ID_PATH)
    ResponseEntity<PatientResponseDTO> deletePatientByIdByPatient(@PathVariable String patientId);

}
