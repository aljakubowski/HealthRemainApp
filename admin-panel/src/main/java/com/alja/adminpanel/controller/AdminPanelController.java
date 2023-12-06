package com.alja.adminpanel.controller;

import com.alja.adminpanel.controller_resources.AdminPanelResource;
import com.alja.adminpanel.service.AdminPanelService;
import com.alja.errors.PhysicianError;
import com.alja.exception.PhysicianException;
import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianRegisteredResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AdminPanelController implements AdminPanelResource {

    private final AdminPanelService adminPanelService;

    @Override
    public PhysicianResponseDTO getPhysicianById(String physicianId) {
        return null;
    }

    @Override
    public List<PhysicianResponseDTO> getAllPhysicians() {
        return adminPanelService.getAllPhysicians();
    }

    @Override
    public PhysicianRegisteredResponseDTO registerNewPhysician(NewPhysicianDTO newPhysicianDTO) {
        return adminPanelService.registerNewPhysician(newPhysicianDTO);
    }

    @Override
    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        return adminPanelService.getAllSpecializations();
    }

    @Override
    public void addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        throw new PhysicianException(PhysicianError.PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR);
//        adminPanelService.addNewSpecialization(physicianSpecializationDTO);
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