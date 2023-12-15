package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.physician.controller_resources.PhysiciansResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PhysiciansResource {

    String RESOURCE_PATH = "/api/v1/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";

    @GetMapping(PHYSICIAN_ID_PATH)
    PhysicianResponseDTO getPhysicianById(@PathVariable String physicianId);

    @GetMapping
    List<PhysicianResponseDTO> getAllPhysicians();

    @PostMapping
    void registerNewPhysician(@Valid @RequestBody PhysicianRegisterDTO newPhysicianDTO);
    
    //// TODO: 15/12/2023 EDIT Phys
    // TODO: 15/12/2023 DELETE Phys 
}
