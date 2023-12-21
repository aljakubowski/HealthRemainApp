package com.alja.patient.controller_resource;

import com.alja.common.annotation.PatientData;
import com.alja.common.enums.PatientDataFormat;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.patient.controller_resource.PatientResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PatientResource {

    String RESOURCE_PATH = "/api/v1/patient";
    String PATIENT_ID_PATH = "/{patientId}";
    String PATIENT_DETAILS = "/details";
    String PATIENT_VISITS = "/visits";


    @PostMapping
    PatientResponseDTO registerNewPatient(@Valid @RequestBody PatientRegisterDTO patientRegisterDTODTO);

    @GetMapping
    List<PatientResponseDTO> getAllPatients();
//fixme refactor when working
//    @GetMapping(PATIENT_ID_PATH)
//    PatientResponseDTO getPatientById(@PathVariable String patientId);

//    @GetMapping(PATIENT_ID_PATH + PATIENT_DETAILS)
//    PatientResponseDTO getPatientById(@PathVariable("patientId") String patientId,
//                                                 @RequestParam(required = false) boolean details,
//                                                 @RequestParam(required = false) boolean visits);

    @GetMapping(PATIENT_ID_PATH + PATIENT_DETAILS)
    PatientResponseDTO getPatientById(@PathVariable("patientId") String patientId,
                                      @RequestParam(required = false) @PatientData String dataFormat);

    @PutMapping(PATIENT_ID_PATH)
    PatientResponseDTO updatePatient(@PathVariable String patientId,
                                         @Valid @RequestBody PatientUpdateDTO patientUpdateDTO);

    @DeleteMapping(PATIENT_ID_PATH)
    PatientResponseDTO deletePatientById(@PathVariable String patientId);

}
