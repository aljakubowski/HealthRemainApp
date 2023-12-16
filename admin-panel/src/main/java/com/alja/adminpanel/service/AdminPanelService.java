package com.alja.adminpanel.service;

import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianRegisteredResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.alja.physician.PhysicianLogs.*;

@AllArgsConstructor
@Service
@Slf4j
public class AdminPanelService {

    private final PhysicianClient physicianClient;
    private final LogService logService;
    //fixme handle responses?
    public List<PhysicianResponseDTO> getAllPhysicians() {
        return physicianClient.getAllPhysicians();
    }

    public PhysicianRegisteredResponseDTO registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO){
        LocalDateTime registrationDate = LocalDateTime.now();
        physicianClient.registerNewPhysician(newPhysicianDTO);
        //fixme from client response
        return PhysicianRegisteredResponseDTO.builder()
                .firstName(newPhysicianDTO.getFirstName())
                .lastName(newPhysicianDTO.getLastName())
                .physiciansSpecialization(newPhysicianDTO.getPhysicianSpecialization())
                .registrationDate(registrationDate)
                .build();
    }


    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        logService.logOperation(GET_SPECIALIZATIONS.logMessage);
        return physicianClient.getAllSpecializations();
    }

    public void addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        logService.logOperation(ADD_SPECIALIZATION.logMessage, physicianSpecializationDTO.getSpecializationName());
        physicianClient.addNewSpecialization(physicianSpecializationDTO);
    }

    public void updateSpecialization(String specializationName, String specializationNewName) {
        logService.logOperation(UPDATE_SPECIALIZATION.logMessage, specializationName, specializationNewName);
        physicianClient.updateSpecialization(specializationName, specializationNewName);
    }

    public void deleteSpecialization(String specializationName) {
        logService.logOperation(DELETE_SPECIALIZATION.logMessage, specializationName);
        physicianClient.deleteSpecialization(specializationName);
    }
}
