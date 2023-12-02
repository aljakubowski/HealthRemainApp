package com.alja.adminpanel.controller;

import com.alja.adminpanel.controller_resources.AdminPanelResource;
import com.alja.adminpanel.service.AdminPanelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianResponseDTO;

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
    public void registerNewPhysician(NewPhysicianDTO newPhysicianDTO) {
        adminPanelService.registerNewPhysician(newPhysicianDTO);
    }

}
