package com.alja.visit.controller;

import com.alja.visit.controller_resource.VisitAdminResource;
import com.alja.visit.dto.*;
import com.alja.visit.service.VisitAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class VisitAdminController implements VisitAdminResource {

    private final VisitAdminService visitAdminService;

    @Override
    public VisitSimpleResponseDTO addNewVisit(VisitNewDTO visitNewDTO) {
        return visitAdminService.addNewVisit(visitNewDTO);
    }

    @Override
    public VisitResponseDTO getVisitById(String visitId) {
        return visitAdminService.getVisitById(visitId);
    }

//    @Override
//    public List<VisitSimpleResponseDTO> getAllVisits() {
//        return visitAdminService.getAllVisit();
//    }

    @Override
    public List<VisitSimpleResponseDTO> getVisitsWithFilter(VisitFilterDTO visitFilter) {
        return visitAdminService.getVisitsWithFilter(visitFilter);
    }

    @Override
    public VisitSimpleResponseDTO updateVisit(String visitId, VisitUpdateDTO visitUpdateDTO) {
        return visitAdminService.updateVisit(visitId, visitUpdateDTO);
    }

    @Override
    public VisitSimpleResponseDTO deleteVisitById(String visitId) {
        return visitAdminService.deleteVisitById(visitId);
    }
}
