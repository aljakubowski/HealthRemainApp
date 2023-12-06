package com.alja.physician.service;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.model.PhysicianSpecializationEntity;
import com.alja.physician.model.mapper.PhysicianSpecializationMapper;
import com.alja.physician.model.repository.PhysicianSpecializationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alja.physician.PhysicianLogs.*;

@Slf4j
@AllArgsConstructor
@Service
public class PhysiciansSpecializationService {

    private final PhysicianSpecializationRepository physicianSpecializationRepository;
    private final PhysicianSpecializationMapper physicianSpecializationMapper;
    private final LogService logService;

    //// TODO: 04/12/2023 ERRORS HANDLER
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
        if (!specializationExists(specializationName)) {
            physicianSpecializationRepository.save(PhysicianSpecializationEntity.builder()
                    .specializationName(specializationName)
                    .build());
        } else {
            //todo create exceptions
            throw new RuntimeException();
        }
    }

    public void updateSpecialization(String specializationName, String newSpecializationName) {
        logService.logOperation(UPDATE_SPECIALIZATION.logMessage, specializationName, newSpecializationName);
        physicianSpecializationRepository.findBySpecializationName(specializationName)
                .ifPresentOrElse(entity -> updateSpecializationEntity(entity, newSpecializationName),
                        () -> addNewSpecialization(PhysicianSpecializationDTO
                                .builder()
                                .specializationName(newSpecializationName)
                                .build()));
    }

    public void deleteSpecialization(String specializationName) {
        logService.logOperation(DELETE_SPECIALIZATION.logMessage, specializationName);
        physicianSpecializationRepository.deleteBySpecializationName(specializationName);
    }

    private void updateSpecializationEntity(PhysicianSpecializationEntity physicianSpecializationEntity,
                                            String newSpecializationName) {
        physicianSpecializationEntity.updateSpecializationName(newSpecializationName);
        physicianSpecializationRepository.save(physicianSpecializationEntity);
    }

    private boolean specializationExists(String specializationName){
        return physicianSpecializationRepository.existsBySpecializationName(specializationName);
    }

}
