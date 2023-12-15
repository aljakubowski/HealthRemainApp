package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysiciansResource;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
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
    public PhysicianResponseDTO getPhysicianById(String doctorId) {
        //// TODO: 15/12/2023
        return physicianService.getPhysicianById(doctorId);
    }

    @Override
    public List<PhysicianResponseDTO> getAllPhysicians() {
        // TODO: 15/12/2023 define DTO response / format? 
        return physicianService.getAllPhysicians();
    }

    @Override
    public void registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO) {
        //// TODO: 15/12/2023 define DTO response / format?
        physicianService.registerNewPhysician(newPhysicianDTO);
    }

}
