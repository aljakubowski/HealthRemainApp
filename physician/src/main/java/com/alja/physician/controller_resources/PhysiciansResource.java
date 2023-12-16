package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.physician.controller_resources.PhysiciansResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PhysiciansResource {

    String RESOURCE_PATH = "/api/v1/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";
    String PHYSICIAN_DETAILS = "/{details}";


    @PostMapping
    PhysicianResponseDTO registerNewPhysician(@Valid @RequestBody PhysicianRegisterDTO newPhysicianDTO);

    @GetMapping
    List<PhysicianResponseDTO> getAllPhysicians();

    @GetMapping(PHYSICIAN_ID_PATH + PHYSICIAN_DETAILS)
    PhysicianResponseDTO getPhysicianById(@PathVariable String physicianId,
                                          @PathVariable boolean details);

    @PutMapping(PHYSICIAN_ID_PATH)
    PhysicianResponseDTO updatePhysician(@PathVariable String physicianId,
                                         @Valid @RequestBody PhysicianUpdateDTO physicianUpdateDTO);

    @DeleteMapping(PHYSICIAN_ID_PATH)
    PhysicianResponseDTO deletePhysicianById(@PathVariable String physicianId);

}
