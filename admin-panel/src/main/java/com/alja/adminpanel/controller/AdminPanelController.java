package com.alja.adminpanel.controller;

import com.alja.adminpanel.controller_resources.AdminPanelResource;
import com.alja.adminpanel.service.AdminPanelService;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianRegisteredResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminPanelController implements AdminPanelResource {

    private final AdminPanelService adminPanelService;
    //todo return ResponseEntity as responses

    @Override
    public PhysicianResponseDTO getPhysicianById(String physicianId) {
        return null;
    }

    @Override
    public List<PhysicianResponseDTO> getAllPhysicians() {
        return adminPanelService.getAllPhysicians();
    }

    @Override
    public PhysicianRegisteredResponseDTO registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO) {
        return adminPanelService.registerNewPhysician(newPhysicianDTO);
    }

    @Override
    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        return adminPanelService.getAllSpecializations();
    }

    @Override
    public ResponseEntity<PhysicianSpecializationDTO> addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        adminPanelService.addNewSpecialization(physicianSpecializationDTO);
        //fixme return of response entity? ResponseEntity<PhysicianSpecializationDTO> - response from service
        return new ResponseEntity<>(physicianSpecializationDTO, HttpStatus.CREATED);
    }

    @Override
    public void updateSpecialization(String specializationName, String specializationNewName) {
        adminPanelService.updateSpecialization(specializationName, specializationNewName);
    }

    @Override
    public void deleteSpecialization(String specializationName) {
        adminPanelService.deleteSpecialization(specializationName);
    }

}