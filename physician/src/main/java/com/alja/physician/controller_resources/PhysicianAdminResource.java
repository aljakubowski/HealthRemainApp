package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.physician.controller_resources.PhysicianAdminResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PhysicianAdminResource {

    String RESOURCE_PATH = "/api/v1/physician/admin";
    String PHYSICIAN_ID_PATH = "/{physicianId}";
    String PHYSICIAN_DETAILS = "/{details}";


    @PostMapping
    ResponseEntity<PhysicianResponseDTO> registerNewPhysician(@Valid @RequestBody PhysicianRegisterDTO newPhysicianDTO);

    @GetMapping
    ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians();

    @GetMapping(PHYSICIAN_ID_PATH + PHYSICIAN_DETAILS)
    ResponseEntity<PhysicianResponseDTO> getPhysicianById(@PathVariable String physicianId,
                                          @PathVariable boolean details);

    @PutMapping(PHYSICIAN_ID_PATH)
    ResponseEntity<PhysicianResponseDTO> updatePhysician(@PathVariable String physicianId,
                                         @Valid @RequestBody PhysicianUpdateDTO physicianUpdateDTO);

    @DeleteMapping(PHYSICIAN_ID_PATH)
    ResponseEntity<PhysicianResponseDTO> deletePhysicianById(@PathVariable String physicianId);

}
