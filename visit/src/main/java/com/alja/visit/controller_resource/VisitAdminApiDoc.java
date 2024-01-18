package com.alja.visit.controller_resource;

import com.alja.visit.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "admin api", description = "creating and managing visits")
public interface VisitAdminApiDoc {

    @Operation(summary = "Visit add")
    ResponseEntity<VisitSimpleResponseDTO> addNewVisit(VisitNewDTO visitNewDTO);

    @Parameter(name = "visitId", required = true)
    @Operation(summary = "Visit get by id")
    ResponseEntity<VisitResponseDTO> getVisitById(String visitId);

    @Operation(summary = "Visit get by filter")
    ResponseEntity<List<VisitSimpleResponseDTO>> getVisitsWithFilter(VisitFilterDTO visitFilter);

    @Parameter(name = "visitId", required = true)
    @Operation(summary = "Visit update info")
    ResponseEntity<VisitSimpleResponseDTO> updateVisit(String visitId, VisitUpdateDTO visitUpdateDTO);

    @Parameter(name = "visitId", required = true)
    @Operation(summary = "Visit delete")
    ResponseEntity<VisitSimpleResponseDTO> deleteVisitById(String visitId);

}
