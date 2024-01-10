package com.alja.visit.controller_resource;

import com.alja.visit.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.visit.controller_resource.VisitAdminResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface VisitPatientResource {

    String RESOURCE_PATH = "/api/v1/visit/patient";
    String VISIT_ID_PATH = "/{visitId}";

    //todo create Patient module ? to register, update data, manage visits ?


    //todo implement methods
    //  appoint visit, cancel visit
    //  get visit by id
    //  get all patient visits
    //      get all RESERVED vs COMPLETED visits?
    //  get (search) all visists by physician / specialization ?
    // get all physicianRecommendations for all visits?

//    @PostMapping
//    VisitSimpleResponseDTO addNewVisit(@Valid @RequestBody VisitNewDTO visitNewDTO);
//
//    @GetMapping(VISIT_ID_PATH)
//    VisitResponseDTO getVisitById(@PathVariable String visitId);
//
//
//    //todo visits GET filter with sort?     order, sort, pageable  ???
//    @GetMapping
//    List<VisitSimpleResponseDTO> getVisitsWithFilter(@Valid @RequestBody(required = false) VisitFilterDTO visitFilter);
//    //fixme is needed?
//
//    @GetMapping
//    List<VisitSimpleResponseDTO> getAllVisits();
//
//
//    @PutMapping(VISIT_ID_PATH)
//    VisitResponseDTO updateVisit(@PathVariable String visitId,
//                                 @Valid @RequestBody VisitUpdateDTO visitUpdateDTO);
//
//    @DeleteMapping(VISIT_ID_PATH)
//    VisitResponseDTO deleteVisitById(@PathVariable String visitId);

}
