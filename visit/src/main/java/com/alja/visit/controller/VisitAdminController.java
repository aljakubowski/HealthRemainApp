package com.alja.visit.controller;

import com.alja.visit.controller_resource.VisitAdminResource;
import com.alja.visit.dto.*;
import com.alja.visit.service.VisitAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class VisitAdminController implements VisitAdminResource {
    //todo int test
    private final VisitAdminService visitAdminService;

    @Override
    public ResponseEntity<VisitSimpleResponseDTO> addNewVisit(VisitNewDTO visitNewDTO) {
        VisitSimpleResponseDTO visitSimpleResponseDTO = visitAdminService.addNewVisit(visitNewDTO);
        return new ResponseEntity<>(visitSimpleResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VisitResponseDTO> getVisitById(String visitId) {
        VisitResponseDTO visitResponseDTO = visitAdminService.getVisitById(visitId);
        return new ResponseEntity<>(visitResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<VisitSimpleResponseDTO>> getVisitsWithFilter(VisitFilterDTO visitFilter) {
        List<VisitSimpleResponseDTO> visitSimpleResponsesDTO = visitAdminService.getVisitsWithFilter(visitFilter);;
        return new ResponseEntity<>(visitSimpleResponsesDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VisitSimpleResponseDTO> updateVisit(String visitId, VisitUpdateDTO visitUpdateDTO) {
        VisitSimpleResponseDTO visitSimpleResponseDTO = visitAdminService.updateVisit(visitId, visitUpdateDTO);;
        return new ResponseEntity<>(visitSimpleResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VisitSimpleResponseDTO> deleteVisitById(String visitId) {
        VisitSimpleResponseDTO visitSimpleResponseDTO = visitAdminService.deleteVisitById(visitId);
        return new ResponseEntity<>(visitSimpleResponseDTO, HttpStatus.OK);
    }
}
