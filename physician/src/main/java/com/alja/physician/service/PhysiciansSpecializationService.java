package com.alja.physician.service;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.errors.PhysicianError;
import com.alja.exception.PhysicianException;
import com.alja.physician.model.PhysicianSpecializationEntity;
import com.alja.physician.model.mapper.PhysicianSpecializationMapper;
import com.alja.physician.model.repository.PhysicianSpecializationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alja.physician.PhysicianLogs.*;

@AllArgsConstructor
@Service
public class PhysiciansSpecializationService {

    private final PhysicianSpecializationRepository physicianSpecializationRepository;
    private final PhysicianSpecializationMapper physicianSpecializationMapper;
    private final LogService logService;

    //// TODO: 04/12/2023 TEST UNIT + INTEGRATION

    public List<PhysicianSpecializationDTO> getAllSpecializations() {
        logService.logOperation(GET_SPECIALIZATIONS.logMessage);
        return physicianSpecializationRepository.findAll().stream()
                .map(physicianSpecializationMapper::specializationEntityToDto)
                .collect(Collectors.toList());
    }

    public void addNewSpecialization(PhysicianSpecializationDTO physicianSpecializationDTO) {
        String specializationName = physicianSpecializationDTO.getSpecializationName();
        logService.logOperation(ADD_SPECIALIZATION.logMessage, specializationName);
        validateIfEntityAlreadyExists(specializationName);
        physicianSpecializationRepository.save(PhysicianSpecializationEntity.builder()
                .specializationName(specializationName)
                .build());
    }

    public void updateSpecialization(String specializationName, String newSpecializationName) {
        logService.logOperation(UPDATE_SPECIALIZATION.logMessage, specializationName, newSpecializationName);
        validateIfEntityExists(specializationName);
        validateIfEntityAlreadyExists(newSpecializationName);
        updateSpecializationEntity(physicianSpecializationRepository.findBySpecializationName(specializationName),
                newSpecializationName);
    }

    public void deleteSpecialization(String specializationName) {
        logService.logOperation(DELETE_SPECIALIZATION.logMessage, specializationName);
        validateIfEntityExists(specializationName);
        physicianSpecializationRepository.deleteBySpecializationName(specializationName);
    }

    private void updateSpecializationEntity(PhysicianSpecializationEntity physicianSpecializationEntity,
                                            String newSpecializationName) {
        physicianSpecializationEntity.updateSpecializationName(newSpecializationName);
        physicianSpecializationRepository.save(physicianSpecializationEntity);
    }


    private void validateIfEntityExists(String specializationName){
        //todo dlaczego tu nie rzuca bledem w physician app ?
        if (!physicianSpecializationRepository.existsBySpecializationName(specializationName)){
            throw new PhysicianException(PhysicianError.PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR);
        }
    }

    private void validateIfEntityAlreadyExists(String specializationName){
        if (physicianSpecializationRepository.existsBySpecializationName(specializationName)){
            throw new PhysicianException(PhysicianError.PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR);
        }
    }

}
