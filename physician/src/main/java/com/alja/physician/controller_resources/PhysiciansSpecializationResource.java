package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.physician.controller_resources.PhysiciansSpecializationResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PhysiciansSpecializationResource {

    String RESOURCE_PATH = "/api/v1/physician/admin/specialization";

    @GetMapping
    ResponseEntity<List<PhysicianSpecializationDTO>> getAllSpecializations();

    @PostMapping
    ResponseEntity<PhysicianSpecializationDTO> addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping()
    ResponseEntity<PhysicianSpecializationDTO> updateSpecialization(@RequestParam("specializationName") String specializationName,
                                                                    @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping()
    ResponseEntity<PhysicianSpecializationDTO> deleteSpecialization(@RequestParam("specializationName") String specializationName);
}
