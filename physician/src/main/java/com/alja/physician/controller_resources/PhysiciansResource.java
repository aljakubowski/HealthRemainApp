package com.alja.physician.controller_resources;

import com.alja.physician.model.dto.NewPhysicianDTO;
import com.alja.physician.model.dto.PhysicianResponseDTO;
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
    void registerNewPhysician(@RequestBody NewPhysicianDTO newPhysicianDTO);
}
