package com.alja.visit.controller_resource;

import com.alja.visit.dto.VisitCheckResponseDTO;
import com.alja.visit.dto.VisitCommonFilterDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.visit.controller_resource.VisitPatientResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface VisitPatientResource {

    String RESOURCE_PATH = "/api/v1/visit";
    String PATIENT_ID_PATH = "/{patientId}";
    String CHECK_ID_PATH = "/check/{checkId}";
    String VISIT_ID_PATH = "/{visitId}";

    @GetMapping(PATIENT_ID_PATH)
    List<VisitSimpleResponseDTO> getAllPatientVisits(@PathVariable("patientId") String patientId);

    @GetMapping()
    List<VisitSimpleResponseDTO> searchAvailableVisitsWithFilter(@Valid @RequestBody VisitCommonFilterDTO visitCommonFilterDTO);

    @GetMapping(PATIENT_ID_PATH + VISIT_ID_PATH)
    VisitResponseDTO getVisitById(@PathVariable String patientId, @PathVariable String visitId);

    @PostMapping(PATIENT_ID_PATH + VISIT_ID_PATH)
    VisitSimpleResponseDTO makeVisitAppointment(@PathVariable("patientId") String patientId, @PathVariable("visitId") String visitId);

    @PutMapping(PATIENT_ID_PATH + VISIT_ID_PATH)
    VisitSimpleResponseDTO cancelVisitAppointment(@PathVariable("patientId") String patientId, @PathVariable("visitId") String visitId);

    @GetMapping(CHECK_ID_PATH)
    VisitCheckResponseDTO checkVisits(@PathVariable("checkId") String checkId);
}
