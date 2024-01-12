package com.alja.physician.client;

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

    String PHYSICIAN_ADMIN = "api/v1/physician/admin";
    String SPECIALIZATION_ADMIN = "api/v1/physician/admin/specialization";
    String PHYSICIAN_PATIENT = "api/v1/physician";
    String PHYSICIAN_ID_PATH = "/{physicianId}";
    String PHYSICIAN_DETAILS = "/{details}";


    @PostMapping(PHYSICIAN_ADMIN)
    PhysicianResponseDTO registerNewPhysician(@Valid @RequestBody PhysicianRegisterDTO newPhysicianDTO);

    @GetMapping(PHYSICIAN_ADMIN)
    List<PhysicianResponseDTO> getAllPhysicians();

    @GetMapping(PHYSICIAN_ADMIN + PHYSICIAN_ID_PATH + PHYSICIAN_DETAILS)
    PhysicianResponseDTO getPhysicianById(@PathVariable("physicianId") String physicianId,
                                          @PathVariable("details") boolean details);

    @PutMapping(PHYSICIAN_ADMIN + PHYSICIAN_ID_PATH)
    PhysicianResponseDTO updatePhysician(@PathVariable("physicianId") String physicianId,
                                         @Valid @RequestBody PhysicianUpdateDTO physicianUpdateDTO);

    @DeleteMapping(PHYSICIAN_ADMIN + PHYSICIAN_ID_PATH)
    PhysicianResponseDTO deletePhysicianById(@PathVariable("physicianId") String physicianId);


    @GetMapping(SPECIALIZATION_ADMIN)
    List<PhysicianSpecializationDTO> getAllSpecializations();

    @PostMapping(SPECIALIZATION_ADMIN)
    PhysicianSpecializationDTO addNewSpecialization(@Valid @RequestBody PhysicianSpecializationDTO physicianSpecializationDTO);

    @PutMapping(SPECIALIZATION_ADMIN)
    PhysicianSpecializationDTO updateSpecialization(@RequestParam("specializationName") String specializationName,
                                                    @RequestParam("specializationNewName") String specializationNewName);

    @DeleteMapping(SPECIALIZATION_ADMIN)
    PhysicianSpecializationDTO deleteSpecialization(@RequestParam("specializationName") String specializationName);

    @GetMapping(PHYSICIAN_PATIENT)
    List<PhysicianResponseDTO> getAllPhysiciansByPatient();

    @GetMapping(PHYSICIAN_PATIENT + PHYSICIAN_ID_PATH + PHYSICIAN_DETAILS)
    PhysicianResponseDTO getPhysicianByIdByPatient(@PathVariable("physicianId") String physicianId,
                                                   @PathVariable("details") boolean details);

}
