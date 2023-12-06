package com.alja.physician.client;

import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("physician")
public interface PhysicianClient {

    String PHYSICIAN = "api/v1/physician";
    String SPECIALIZATION = "api/v1/specialization";
    String SPECIALIZATION_NAME_PATH = "/{specializationName}";

    @GetMapping(PHYSICIAN)
    List<PhysicianResponseDTO> getAllPhysicians();

    @PostMapping(PHYSICIAN)
    void registerNewPhysician(@RequestBody NewPhysicianDTO newPhysicianDTO);


    @GetMapping(SPECIALIZATION)
    List<PhysicianSpecializationDTO> getAllSpecializations();

    @PostMapping(SPECIALIZATION)
    void addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping(SPECIALIZATION)
    void updateSpecialization(@RequestParam("specializationName") String specializationName,
                              @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping(SPECIALIZATION)
    void deleteSpecialization(@RequestParam("specializationName") String specializationName);

}
