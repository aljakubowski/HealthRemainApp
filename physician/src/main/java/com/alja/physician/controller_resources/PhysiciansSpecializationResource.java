package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.physician.controller_resources.PhysiciansSpecializationResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PhysiciansSpecializationResource {

    String RESOURCE_PATH = "/api/v1/specialization";

    @GetMapping
    List<PhysicianSpecializationDTO> getAllSpecializations();

    @PostMapping
    void addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping()
    void updateSpecialization(@RequestParam("specializationName") String specializationName,
                              @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping()
    void deleteSpecialization(@RequestParam("specializationName") String specializationName);
}
