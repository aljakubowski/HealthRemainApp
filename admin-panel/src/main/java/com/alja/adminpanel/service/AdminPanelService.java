package com.alja.adminpanel.service;

import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.alja.physician.PhysicianLogs.*;

@AllArgsConstructor
@Service
@Slf4j
public class AdminPanelService {

    private final PhysicianClient physicianClient;
    private final LogService logService;

    public PhysicianResponseDTO registerNewPhysician(PhysicianRegisterDTO newPhysicianDTO) {
        logService.logOperation(REGISTER_NEW_PHYSICIAN.logMessage,
                newPhysicianDTO.getFirstName(),
                newPhysicianDTO.getLastName(),
                newPhysicianDTO.getPhysicianSpecialization());
        return physicianClient.registerNewPhysician(newPhysicianDTO);
    }

    public List<PhysicianResponseDTO> getAllPhysicians() {
        logService.logOperation(GET_ALL_PHYSICIANS.logMessage);
        return physicianClient.getAllPhysicians();
    }

    public PhysicianResponseDTO getPhysicianById(String physicianId, boolean details) {
        logService.logOperation(GET_PHYSICIAN_BY_ID.logMessage, physicianId);
        return physicianClient.getPhysicianById(physicianId, details);
    }

    public PhysicianResponseDTO updatePhysician(String physicianId, PhysicianUpdateDTO physicianUpdateDTO) {
        logService.logOperation(UPDATE_PHYSICIAN.logMessage, physicianId);
        return physicianClient.updatePhysician(physicianId, physicianUpdateDTO);
    }

    public PhysicianResponseDTO deletePhysicianById(@PathVariable String physicianId) {
        logService.logOperation(DELETE_PHYSICIAN_BY_ID.logMessage, physicianId);
        return physicianClient.deletePhysicianById(physicianId);
    }


    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        logService.logOperation(GET_SPECIALIZATIONS.logMessage);
        return physicianClient.getAllSpecializations();
    }

    public PhysicianSpecializationDTO addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        logService.logOperation(ADD_SPECIALIZATION.logMessage, physicianSpecializationDTO.getSpecializationName());
        return physicianClient.addNewSpecialization(physicianSpecializationDTO);
    }

    public PhysicianSpecializationDTO updateSpecialization(String specializationName, String specializationNewName) {
        logService.logOperation(UPDATE_SPECIALIZATION.logMessage, specializationName, specializationNewName);
        return physicianClient.updateSpecialization(specializationName, specializationNewName);
    }

    public PhysicianSpecializationDTO deleteSpecialization(String specializationName) {
        logService.logOperation(DELETE_SPECIALIZATION.logMessage, specializationName);
        return physicianClient.deleteSpecialization(specializationName);
    }
}
