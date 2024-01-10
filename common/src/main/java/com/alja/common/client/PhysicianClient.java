package com.alja.common.client;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("physician")
public interface PhysicianClient {

    String PHYSICIAN = "api/v1/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";
    String PHYSICIAN_DETAILS = "/{details}";
    String SPECIALIZATION = "api/v1/specialization";


    @PostMapping(PHYSICIAN)
    PhysicianResponseDTO registerNewPhysician(@Valid @RequestBody PhysicianRegisterDTO newPhysicianDTO);

    @GetMapping(PHYSICIAN)
    List<PhysicianResponseDTO> getAllPhysicians();

    @GetMapping(PHYSICIAN + PHYSICIAN_ID_PATH + PHYSICIAN_DETAILS)
    PhysicianResponseDTO getPhysicianById(@PathVariable("physicianId") String physicianId,
                                          @PathVariable("details") boolean details);

    @PutMapping(PHYSICIAN + PHYSICIAN_ID_PATH)
    PhysicianResponseDTO updatePhysician(@PathVariable("physicianId") String physicianId,
                                         @Valid @RequestBody PhysicianUpdateDTO physicianUpdateDTO);

    @DeleteMapping(PHYSICIAN + PHYSICIAN_ID_PATH)
    PhysicianResponseDTO deletePhysicianById(@PathVariable("physicianId") String physicianId);


    @GetMapping(SPECIALIZATION)
    List<PhysicianSpecializationDTO> getAllSpecializations();

    @PostMapping(SPECIALIZATION)
    PhysicianSpecializationDTO addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping(SPECIALIZATION)
    PhysicianSpecializationDTO updateSpecialization(@RequestParam("specializationName") String specializationName,
                              @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping(SPECIALIZATION)
    PhysicianSpecializationDTO deleteSpecialization(@RequestParam("specializationName") String specializationName);

}
