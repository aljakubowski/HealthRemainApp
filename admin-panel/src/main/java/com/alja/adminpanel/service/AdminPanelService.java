package com.alja.adminpanel.service;

import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianRegisteredResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AdminPanelService {

    private final PhysicianClient physicianClient;

    public List<PhysicianResponseDTO> getAllPhysicians() {
        return physicianClient.getAllPhysicians();
    }

    public PhysicianRegisteredResponseDTO registerNewPhysician(NewPhysicianDTO newPhysicianDTO){
        LocalDateTime registrationDate = LocalDateTime.now();
        physicianClient.registerNewPhysician(newPhysicianDTO);
        return PhysicianRegisteredResponseDTO.builder()
                .firstName(newPhysicianDTO.getFirstName())
                .lastName(newPhysicianDTO.getLastName())
                .physiciansSpecialization(newPhysicianDTO.getPhysicianSpecialization())
                .registrationDate(registrationDate)
                .build();
    }


    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        return physicianClient.getAllSpecializations();
    }

    public void addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        physicianClient.addNewSpecialization(physicianSpecializationDTO);
    }

    public void updateSpecialization(String specializationName, String specializationNewName) {
        physicianClient.updateSpecialization(specializationName, specializationNewName);
    }

    public void deleteSpecialization(String specializationName) {
        physicianClient.deleteSpecialization(specializationName);
    }
}
