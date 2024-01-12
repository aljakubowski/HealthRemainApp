package com.alja.physician.controller_resources;

import com.alja.physician.dto.PhysicianResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.alja.physician.controller_resources.PhysicianPatientResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface PhysicianPatientResource {

    String RESOURCE_PATH = "/api/v1/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";

    @GetMapping
    ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians();

    @GetMapping(PHYSICIAN_ID_PATH)
    ResponseEntity<PhysicianResponseDTO> getPhysicianById(@PathVariable String physicianId);

}
