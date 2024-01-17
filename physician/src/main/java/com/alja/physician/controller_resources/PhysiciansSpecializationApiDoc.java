package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "physician-specialization-c", description = "Admin API for physician specialization control")
public interface PhysiciansSpecializationApiDoc {


    @Operation(summary = "Physician specialization list")
    ResponseEntity<List<PhysicianSpecializationDTO>> getAllSpecializations();

    @Operation(summary = "Physician specialization add")
    ResponseEntity<PhysicianSpecializationDTO> addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO);

    @Operation(summary = "Physician specialization update",
            parameters = {
                    @Parameter(name = "specializationName", description = "physician specialization current name", required = true),
                    @Parameter(name = "specializationNewName", description = "physician specialization new name", required = true)})
    ResponseEntity<PhysicianSpecializationDTO> updateSpecialization(String specializationName, String specializationNewName);

    @Parameter(name = "specializationName", required = true)
    @Operation(summary = "Physician specialization delete")
    ResponseEntity<PhysicianSpecializationDTO> deleteSpecialization(String specializationName);
}
