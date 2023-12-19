package com.alja.adminpanel.controller;

import com.alja.adminpanel.controller_resources.AdminPanelSpecializationResource;
import com.alja.adminpanel.service.AdminPanelService;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminPanelSpecializationController implements AdminPanelSpecializationResource {

    private final AdminPanelService adminPanelService;

    @Override
    public ResponseEntity<List<PhysicianSpecializationDTO>> getAllSpecializations() {
        List<PhysicianSpecializationDTO> physicianSpecializationDTOs = adminPanelService.getAllSpecializations();
        return new ResponseEntity<>(physicianSpecializationDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        PhysicianSpecializationDTO physicianSpecializationDTOResponse = adminPanelService.addNewSpecialization(physicianSpecializationDTO);
        return new ResponseEntity<>(physicianSpecializationDTOResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> updateSpecialization(String specializationName, String specializationNewName) {
        PhysicianSpecializationDTO physicianSpecializationDTOResponse = adminPanelService.updateSpecialization(specializationName, specializationNewName);
        return new ResponseEntity<>(physicianSpecializationDTOResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> deleteSpecialization(String specializationName) {
        PhysicianSpecializationDTO physicianSpecializationDTOResponse = adminPanelService.deleteSpecialization(specializationName);
        return new ResponseEntity<>(physicianSpecializationDTOResponse, HttpStatus.OK);
    }

}