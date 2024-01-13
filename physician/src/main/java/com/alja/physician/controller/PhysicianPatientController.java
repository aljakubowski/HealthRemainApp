package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysicianPatientResource;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.service.PhysicianService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class PhysicianPatientController implements PhysicianPatientResource {

    private final PhysicianService physicianService;
    private final boolean NO_DETAILS = false;

    @Override
    public ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians() {
        List<PhysicianResponseDTO> physicianResponseDTOs = physicianService.getAllPhysicians();
        return new ResponseEntity<>(physicianResponseDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> getPhysicianById(String physicianId) {
        PhysicianResponseDTO physicianResponseDTO = physicianService.getPhysicianById(physicianId, NO_DETAILS);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }

}
