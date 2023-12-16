package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysiciansResource;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import com.alja.physician.service.PhysicianService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class PhysiciansController implements PhysiciansResource {

    private final PhysicianService physicianService;

    @Override
    public PhysicianResponseDTO registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO) {
        return physicianService.registerNewPhysician(newPhysicianDTO);
    }

    @Override
    public List<PhysicianResponseDTO> getAllPhysicians() {
        return physicianService.getAllPhysicians();
    }

    @Override
    public PhysicianResponseDTO getPhysicianById(String physicianId, boolean details) {
        return physicianService.getPhysicianById(physicianId, details);
    }

    @Override
    public PhysicianResponseDTO updatePhysician(String physicianId, PhysicianUpdateDTO physicianUpdateDTO) {
        return physicianService.updatePhysician(physicianId, physicianUpdateDTO);
    }

    @Override
    public PhysicianResponseDTO deletePhysicianById(String physicianId) {
        return physicianService.deletePhysician(physicianId);
    }
}
