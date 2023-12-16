package com.alja.physician.service;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianResponseDetailedDTO;
import com.alja.physician.dto.PhysicianUpdateDTO;
import com.alja.physician.model.PhysicianEntity;
import com.alja.physician.model.mapper.PhysicianMapper;
import com.alja.physician.model.repository.PhysicianRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alja.physician.PhysicianLogs.*;


@Slf4j
@AllArgsConstructor
@Service
public class PhysicianService {

    private final PhysicianDataValidationService physicianDataValidationService;
    private final PhysicianUpdateService physicianUpdateService;
    private final PhysicianRepository physicianRepository;
    private final PhysicianMapper physicianMapper;
    private final LogService logService;

    // TODO: 15/12/2023 tests
    // TODO: 16/12/2023 create client for admin

    public PhysicianResponseDTO registerNewPhysician(PhysicianRegisterDTO physicianRegisterDTO) {
        logService.logOperation(REGISTER_NEW_PHYSICIAN.logMessage,
                physicianRegisterDTO.getFirstName(),
                physicianRegisterDTO.getLastName(),
                physicianRegisterDTO.getPhysicianSpecialization());
        physicianDataValidationService.validateIfSpecializationExists(physicianRegisterDTO.getPhysicianSpecialization());
        physicianDataValidationService.validateContactDetails(physicianRegisterDTO.getContactDetails());
        PhysicianEntity physicianEntity = physicianMapper.toPhysicianEntity(physicianRegisterDTO);
        physicianRepository.save(physicianEntity);
        return getPhysicianResponseSimple(physicianEntity);
    }

    public List<PhysicianResponseDTO> getAllPhysicians() {
        logService.logOperation(GET_ALL_PHYSICIANS.logMessage);
        return physicianRepository.findAll().stream()
                .map(this::getPhysicianResponseSimple)
                .collect(Collectors.toList());
    }

    public PhysicianResponseDTO getPhysicianById(String physicianId, boolean details) {
        logService.logOperation(GET_PHYSICIAN_BY_ID.logMessage, physicianId);
        PhysicianEntity physicianEntity = physicianDataValidationService.findPhysicianIfPresent(physicianId);
        if (details) {
            return getPhysicianResponseDetailed(physicianEntity);
        } else {
            return getPhysicianResponseSimple(physicianEntity);
        }
    }

    public PhysicianResponseDTO updatePhysician(String physicianId, PhysicianUpdateDTO physicianUpdateDTO) {
        logService.logOperation(UPDATE_PHYSICIAN.logMessage, physicianId);
        PhysicianEntity existingPhysicianEntity = physicianDataValidationService.findPhysicianIfPresent(physicianId);
        PhysicianEntity updatedPhysicianEntity
                = physicianUpdateService.updatePhysician(existingPhysicianEntity, physicianUpdateDTO);
        physicianRepository.save(updatedPhysicianEntity);
        return getPhysicianResponseSimple(updatedPhysicianEntity);
    }

    public PhysicianResponseDTO deletePhysician(String physicianId) {
        logService.logOperation(DELETE_PHYSICIAN_BY_ID.logMessage, physicianId);
        PhysicianEntity physicianEntity = physicianDataValidationService.findPhysicianIfPresent(physicianId);
        physicianRepository.deleteById(physicianEntity.getId());
        return getPhysicianResponseSimple(physicianEntity);
    }


    private PhysicianResponseDTO getPhysicianResponseSimple(PhysicianEntity physicianEntity) {
        return PhysicianResponseDTO.builder()
                .physicianId(physicianEntity.getId())
                .firstName(physicianEntity.getFirstName())
                .lastName(physicianEntity.getLastName())
                .physiciansSpecialization(physicianEntity.getPhysicianSpecialization())
                .build();
    }

    private PhysicianResponseDTO getPhysicianResponseDetailed(PhysicianEntity physicianEntity) {
        return PhysicianResponseDetailedDTO.builder()
                .physicianId(physicianEntity.getId())
                .firstName(physicianEntity.getFirstName())
                .lastName(physicianEntity.getLastName())
                .physiciansSpecialization(physicianEntity.getPhysicianSpecialization())
                .contactDetailsDTO(physicianMapper.contactDetailsToDto(physicianEntity.getContactDetails()))
                .addressDTO(physicianMapper.addressToDto(physicianEntity.getAddress()))
                .registrationDate(physicianEntity.getRegistrationDate())
                .build();
    }

}
