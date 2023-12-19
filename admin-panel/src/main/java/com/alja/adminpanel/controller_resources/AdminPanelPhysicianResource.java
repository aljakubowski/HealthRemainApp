package com.alja.adminpanel.controller_resources;

import com.alja.physician.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.adminpanel.controller_resources.AdminPanelPhysicianResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface AdminPanelPhysicianResource {

    String RESOURCE_PATH = "/api/v1/admin-panel";
    String PHYSICIAN = "/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";
    String PHYSICIAN_DETAILS = "/{details}";

    String SPECIALIZATION_PATH = "/specialization";

    @PostMapping(PHYSICIAN)
    ResponseEntity<PhysicianResponseDTO> registerNewPhysician(@RequestBody PhysicianRegisterDTO newPhysicianDTO);

    @GetMapping(PHYSICIAN)
    ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians();

    @GetMapping(PHYSICIAN + PHYSICIAN_ID_PATH + PHYSICIAN_DETAILS)
    ResponseEntity<PhysicianResponseDTO> getPhysicianById(@PathVariable String physicianId,
                                          @PathVariable boolean details);

    @PutMapping(PHYSICIAN + PHYSICIAN_ID_PATH)
    ResponseEntity<PhysicianResponseDTO> updatePhysician(@PathVariable String physicianId,
                                         @Valid @RequestBody PhysicianUpdateDTO physicianUpdateDTO);

    @DeleteMapping(PHYSICIAN + PHYSICIAN_ID_PATH)
    ResponseEntity<PhysicianResponseDTO> deletePhysicianById(@PathVariable String physicianId);


    @GetMapping(SPECIALIZATION_PATH)
    ResponseEntity<List<PhysicianSpecializationDTO>> getAllSpecializations();

    @PostMapping(SPECIALIZATION_PATH)
    ResponseEntity<PhysicianSpecializationDTO> addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping(SPECIALIZATION_PATH)
    ResponseEntity<PhysicianSpecializationDTO> updateSpecialization(@RequestParam("specializationName") String specializationName,
                              @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping(SPECIALIZATION_PATH)
    ResponseEntity<PhysicianSpecializationDTO> deleteSpecialization(@RequestParam("specializationName") String specializationName);
}