package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysiciansSpecializationApiDoc;
import com.alja.physician.controller_resources.PhysiciansSpecializationResource;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.service.PhysiciansSpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PhysiciansSpecializationController implements PhysiciansSpecializationResource, PhysiciansSpecializationApiDoc {

    private final PhysiciansSpecializationService physiciansSpecializationService;

    @Override
    public ResponseEntity<List<PhysicianSpecializationDTO>> getAllSpecializations() {
        List<PhysicianSpecializationDTO> physicianSpecializationDTOs
                = physiciansSpecializationService.getAllSpecializations();
        return new ResponseEntity<>(physicianSpecializationDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        PhysicianSpecializationDTO physicianSpecializationDTOResponse
                = physiciansSpecializationService.addNewSpecialization(physicianSpecializationDTO);
        return new ResponseEntity<>(physicianSpecializationDTOResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> updateSpecialization(String specializationName, String specializationNewName) {
        PhysicianSpecializationDTO physicianSpecializationDTOResponse
                = physiciansSpecializationService.updateSpecialization(specializationName, specializationNewName);
        return new ResponseEntity<>(physicianSpecializationDTOResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> deleteSpecialization(String specializationName) {
        PhysicianSpecializationDTO physicianSpecializationDTOResponse
                = physiciansSpecializationService.deleteSpecialization(specializationName);
        return new ResponseEntity<>(physicianSpecializationDTOResponse, HttpStatus.OK);
    }
}
