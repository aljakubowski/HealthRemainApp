package com.alja.visit.controller;

import com.alja.visit.controller_resource.VisitPatientResource;
import com.alja.visit.dto.VisitCheckResponseDTO;
import com.alja.visit.dto.VisitCommonFilterDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import com.alja.visit.service.VisitPatientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class VisitPatientController implements VisitPatientResource {

    private final VisitPatientService visitPatientService;

    @Override
    public List<VisitSimpleResponseDTO> getAllPatientVisits(String patientId) {
        return visitPatientService.getAllPatientVisits(patientId);
    }

    @Override
    public List<VisitSimpleResponseDTO> searchAvailableVisitsWithFilter(VisitCommonFilterDTO visitFilter) {
        return visitPatientService.searchAvailableVisitsWithFilter(visitFilter);
    }

    @Override
    public VisitResponseDTO getVisitById(String patientId, String visitId) {
        return visitPatientService.getVisitById(visitId);
    }

    @Override
    public VisitSimpleResponseDTO makeVisitAppointment(String patientId, String visitId) {
        return visitPatientService.makeVisitAppointment(patientId, visitId);
    }

    @Override
    public VisitSimpleResponseDTO cancelVisitAppointment(String patientId, String visitId) {
        return visitPatientService.cancelVisitAppointment(patientId, visitId);
    }

    @Override
    public VisitCheckResponseDTO checkVisits(String checkId) {
        return visitPatientService.checkVisit(checkId);
    }

}
