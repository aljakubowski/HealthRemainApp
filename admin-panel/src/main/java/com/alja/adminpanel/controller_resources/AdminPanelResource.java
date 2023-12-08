package com.alja.adminpanel.controller_resources;

import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianRegisteredResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.adminpanel.controller_resources.AdminPanelResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface AdminPanelResource {

    String RESOURCE_PATH = "/api/v1/admin-panel";
    String PHYSICIAN_PATH = "/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";
    String SPECIALIZATION_PATH = "/specialization";

    @GetMapping(PHYSICIAN_PATH + PHYSICIAN_ID_PATH)
    PhysicianResponseDTO getPhysicianById(@PathVariable String physicianId);

    @GetMapping(PHYSICIAN_PATH)
    List<PhysicianResponseDTO> getAllPhysicians();

    @PostMapping(PHYSICIAN_PATH)
    PhysicianRegisteredResponseDTO registerNewPhysician(@RequestBody NewPhysicianDTO newPhysicianDTO);

    @GetMapping(SPECIALIZATION_PATH)
    List<PhysicianSpecializationDTO> getAllSpecializations();

    @PostMapping(SPECIALIZATION_PATH)
    void addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping(SPECIALIZATION_PATH)
    void updateSpecialization(@RequestParam("specializationName") String specializationName,
                              @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping(SPECIALIZATION_PATH)
    void deleteSpecialization(@RequestParam("specializationName") String specializationName);
}