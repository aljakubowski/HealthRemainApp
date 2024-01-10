package com.alja.visit.controller_resource;

import com.alja.visit.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.visit.controller_resource.VisitAdminResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface VisitAdminResource {

    String RESOURCE_PATH = "/api/v1/visit";
    String VISIT_ID_PATH = "/{visitId}";

    @PostMapping
    VisitSimpleResponseDTO addNewVisit(@Valid @RequestBody VisitNewDTO visitNewDTO);

    @GetMapping(VISIT_ID_PATH)
    VisitResponseDTO getVisitById(@PathVariable String visitId);


    //todo visits GET filter with sort?     order, sort, pageable  ???
    @GetMapping
    List<VisitSimpleResponseDTO> getVisitsWithFilter(@Valid @RequestBody(required = false) VisitFilterDTO visitFilter);

//    @GetMapping
//    List<VisitSimpleResponseDTO> getAllVisits();


    @PutMapping(VISIT_ID_PATH)
    VisitSimpleResponseDTO updateVisit(@PathVariable String visitId,
                                 @Valid @RequestBody VisitUpdateDTO visitUpdateDTO);

    @DeleteMapping(VISIT_ID_PATH)
    VisitSimpleResponseDTO deleteVisitById(@PathVariable String visitId);

}
