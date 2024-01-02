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
    VisitCreatedResponseDTO addNewVisit(@Valid @RequestBody VisitNewDTO visitNewDTO);

    //fixme is needed?
    @GetMapping
    List<VisitCreatedResponseDTO> getAllVisits();

    @GetMapping
    List<VisitResponseDTO> getVisitsWithFilter(@Valid @RequestBody(required = false) VisitFilterDTO visitFilter);


//todo visits GET filter with sort?
//                                  order, sort, pageable

    @PutMapping(VISIT_ID_PATH)
    VisitResponseDTO updateVisit(@PathVariable String visitId,
                                 @Valid @RequestBody VisitUpdateDTO visitUpdateDTO);

    @DeleteMapping(VISIT_ID_PATH)
    VisitResponseDTO deleteVisitById(@PathVariable String visitId);

}
