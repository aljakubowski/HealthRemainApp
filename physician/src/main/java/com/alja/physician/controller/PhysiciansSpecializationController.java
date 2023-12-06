package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysiciansSpecializationResource;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.service.PhysiciansSpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PhysiciansSpecializationController implements PhysiciansSpecializationResource {

    private final PhysiciansSpecializationService physiciansSpecializationService;

    @Override
    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        return physiciansSpecializationService.getAllSpecializations();
    }

    @Override
    public void addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        physiciansSpecializationService.addNewSpecialization(physicianSpecializationDTO);
    }

    @Override
    public void updateSpecialization(String specializationName, String specializationNewName) {
        physiciansSpecializationService.updateSpecialization(specializationName, specializationNewName);
    }

    @Override
    public void deleteSpecialization(String specializationName) {
        physiciansSpecializationService.deleteSpecialization(specializationName);
    }
}
