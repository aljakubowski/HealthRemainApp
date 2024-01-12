package com.alja.visit.client;

import com.alja.visit.dto.VisitCheckResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("visit")
public interface VisitClient {

    String VISIT = "/api/v1/visit";
    String PATIENT_ID_PATH = "/{patientId}";
    String CHECK_ID_PATH = "/check/{checkId}";

    @GetMapping(VISIT + PATIENT_ID_PATH)
    List<VisitSimpleResponseDTO> getAllPatientVisits(@PathVariable("patientId") String patientId);

    @GetMapping(VISIT + CHECK_ID_PATH)
    VisitCheckResponseDTO checkVisits(@PathVariable("checkId") String checkId);
}
