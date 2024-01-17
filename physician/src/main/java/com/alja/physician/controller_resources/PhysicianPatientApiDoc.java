package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "physician-i", description = "Patient API for physician info")
public interface PhysicianPatientApiDoc {

    @Operation(summary = "Physician list")
    ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians();

    @Parameter(name = "physicianId", required = true)
    @Operation(summary = "Physician get with id")
    ResponseEntity<PhysicianResponseDTO> getPhysicianById(@PathVariable String physicianId);

}
