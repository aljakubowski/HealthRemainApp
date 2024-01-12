package com.alja.visit.controller_resource;

import com.alja.visit.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.visit.controller_resource.VisitAdminResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface VisitAdminResource {

    String RESOURCE_PATH = "/api/v1/visit/admin";
    String VISIT_ID_PATH = "/{visitId}";

    @PostMapping
    ResponseEntity<VisitSimpleResponseDTO> addNewVisit(@Valid @RequestBody VisitNewDTO visitNewDTO);

    @GetMapping(VISIT_ID_PATH)
    ResponseEntity<VisitResponseDTO> getVisitById(@PathVariable String visitId);

    @GetMapping
    ResponseEntity<List<VisitSimpleResponseDTO>> getVisitsWithFilter(@Valid @RequestBody(required = false) VisitFilterDTO visitFilter);

    @PutMapping(VISIT_ID_PATH)
    ResponseEntity<VisitSimpleResponseDTO> updateVisit(@PathVariable String visitId,
                                       @Valid @RequestBody VisitUpdateDTO visitUpdateDTO);

    @DeleteMapping(VISIT_ID_PATH)
    ResponseEntity<VisitSimpleResponseDTO> deleteVisitById(@PathVariable String visitId);

}
