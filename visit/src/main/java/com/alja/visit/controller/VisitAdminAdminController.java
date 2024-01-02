package com.alja.visit.controller;

import com.alja.visit.controller_resource.VisitAdminResource;
import com.alja.visit.dto.*;
import com.alja.visit.service.VisitAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class VisitAdminAdminController implements VisitAdminResource {

    private final VisitAdminService visitAdminService;

    @Override
    public VisitCreatedResponseDTO addNewVisit(VisitNewDTO visitNewDTO) {
        return visitAdminService.addNewVisit(visitNewDTO);
    }

    @Override
    public List<VisitCreatedResponseDTO> getAllVisits() {
        return visitAdminService.getAllAvailableVisit();
    }

    //todo implement below methods

    @Override
    public List<VisitResponseDTO> getVisitsWithFilter(VisitFilterDTO visitFilter) {
        return visitAdminService.getVisitsWithFilter(visitFilter);
    }

    @Override
    public VisitResponseDTO updateVisit(String visitId, VisitUpdateDTO visitUpdateDTO) {
        return null;
    }

    @Override
    public VisitResponseDTO deleteVisitById(String visitId) {
        return null;
    }
}
