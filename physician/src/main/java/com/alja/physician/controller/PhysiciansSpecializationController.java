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
    public PhysicianSpecializationDTO addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        return physiciansSpecializationService.addNewSpecialization(physicianSpecializationDTO);
    }

    @Override
    public PhysicianSpecializationDTO updateSpecialization(String specializationName, String specializationNewName) {
        return physiciansSpecializationService.updateSpecialization(specializationName, specializationNewName);
    }

    @Override
    public PhysicianSpecializationDTO deleteSpecialization(String specializationName) {
        return physiciansSpecializationService.deleteSpecialization(specializationName);
    }
}
