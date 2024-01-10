package com.alja.patient.client;

import com.alja.patient.dto.PatientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("patient")
public interface PatientClient {
    String PATIENT = "api/v1/physician";
    String PATIENT_ID_PATH = "/{patientId}";
    String PATIENT_DATA_FORMAT = "/dataFormat";

    @GetMapping(PATIENT + PATIENT_ID_PATH + PATIENT_DATA_FORMAT)
    PatientResponseDTO getPatientById(@PathVariable("patientId") String patientId);
}
