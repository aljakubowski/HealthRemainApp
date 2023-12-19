package com.alja.adminpanel.controller;

import com.alja.adminpanel.controller_resources.AdminPanelPhysicianResource;
import com.alja.adminpanel.service.AdminPanelService;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminPanelPhysicianController implements AdminPanelPhysicianResource {

    private final AdminPanelService adminPanelService;
    //todo split

    @Override
    public ResponseEntity<PhysicianResponseDTO> registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO) {
        PhysicianResponseDTO physicianResponseDTO = adminPanelService.registerNewPhysician(newPhysicianDTO);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PhysicianResponseDTO>> getAllPhysicians() {
        List<PhysicianResponseDTO> physicianResponseDTOs = adminPanelService.getAllPhysicians();
        return new ResponseEntity<>(physicianResponseDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> getPhysicianById(String physicianId, boolean details) {
        PhysicianResponseDTO physicianResponseDTO = adminPanelService.getPhysicianById(physicianId, details);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> updatePhysician(String physicianId, PhysicianUpdateDTO physicianUpdateDTO) {
        PhysicianResponseDTO physicianResponseDTO = adminPanelService.updatePhysician(physicianId, physicianUpdateDTO);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PhysicianResponseDTO> deletePhysicianById(String physicianId) {
        PhysicianResponseDTO physicianResponseDTO = adminPanelService.deletePhysicianById(physicianId);
        return new ResponseEntity<>(physicianResponseDTO, HttpStatus.OK);
    }

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