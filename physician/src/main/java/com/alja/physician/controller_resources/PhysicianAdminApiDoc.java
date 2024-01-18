package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;


@Tag(name = "admin api", description = "managing physicians")
public interface PhysicianAdminApiDoc {


    @Operation(summary = "Physician register")
    ResponseEntity<PhysicianResponseDTO> registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO);

    @Operation(summary = "Physician list")
    ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians();

    @Operation(summary = "Physician get with id",
            parameters = {
                    @Parameter(name = "physicianId", required = true),
                    @Parameter(name = "details", description = "choose data format with details or without: true or false")})
    ResponseEntity<PhysicianResponseDTO> getPhysicianById(String physicianId, boolean details);

    @Parameter(name = "physicianId", required = true)
    @Operation(summary = "Physician update")
    ResponseEntity<PhysicianResponseDTO> updatePhysician(String physicianId, PhysicianUpdateDTO physicianUpdateDTO);

    @Parameter(name = "physicianId", required = true)
    @Operation(summary = "Physician delete")
    ResponseEntity<PhysicianResponseDTO> deletePhysicianById(String physicianId);

}
