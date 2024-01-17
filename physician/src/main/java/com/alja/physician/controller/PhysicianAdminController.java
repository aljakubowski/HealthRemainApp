package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysicianAdminApiDoc;
import com.alja.physician.controller_resources.PhysicianAdminResource;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
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
public class PhysicianAdminController implements PhysicianAdminResource, PhysicianAdminApiDoc {

    private final PhysicianService physicianService;

    @Override
    public ResponseEntity<PhysicianResponseDTO> registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO) {
        PhysicianResponseDTO physicianResponseDTO = physicianService.registerNewPhysician(newPhysicianDTO);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians() {
        List<PhysicianResponseDTO> physicianResponseDTOs = physicianService.getAllPhysicians();
        return new ResponseEntity<>(physicianResponseDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> getPhysicianById(String physicianId, boolean details) {
        PhysicianResponseDTO physicianResponseDTO = physicianService.getPhysicianById(physicianId, details);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> updatePhysician(String physicianId, PhysicianUpdateDTO physicianUpdateDTO) {
        PhysicianResponseDTO physicianResponseDTO = physicianService.updatePhysician(physicianId, physicianUpdateDTO);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> deletePhysicianById(String physicianId) {
        PhysicianResponseDTO physicianResponseDTO = physicianService.deletePhysician(physicianId);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }
}
