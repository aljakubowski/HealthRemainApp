package com.alja.physician.service;

import com.alja.physician.dto.PhysicianRegisterDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.model.PhysicianEntity;
import com.alja.physician.model.mapper.PhysicianMapper;
import com.alja.physician.model.repository.PhysicianRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.alja.physician.PhysicianLogs.REGISTER_NEW_PHYSICIAN;


@Slf4j
@AllArgsConstructor
@Service
public class PhysicianService {

    private final PhysicianDataValidationService physicianDataValidationService;
    private final PhysicianRepository physicianRepository;
    private final PhysicianMapper physicianMapper;
    private final LogService logService;

    // TODO: 15/12/2023 validation
    // TODO: 15/12/2023 tests
    // TODO: 15/12/2023 logs

    public void registerNewPhysician(PhysicianRegisterDTO physicianRegisterDTO) {
        //todo add Response not void
        logService.logOperation(REGISTER_NEW_PHYSICIAN.logMessage,
                physicianRegisterDTO.getFirstName(),
                physicianRegisterDTO.getLastName(),
                physicianRegisterDTO.getPhysicianSpecialization());
        physicianDataValidationService.validateIfSpecializationExists(physicianRegisterDTO.getPhysicianSpecialization());
        physicianDataValidationService.validateContactDetails(physicianRegisterDTO.getContactDetails());
        PhysicianEntity physicianEntity = physicianMapper.toPhysicianEntity(physicianRegisterDTO);
        physicianRepository.save(physicianEntity);
    }


    public PhysicianResponseDTO getPhysicianById(String id) {
        PhysicianEntity physicianEntity = physicianRepository.findById(id).orElse(null);
        // response / full response format
        return getDoctorResponseDTO(physicianEntity);
    }

    public List<PhysicianResponseDTO> getAllPhysicians() {
        List<PhysicianEntity> doctorEntities = physicianRepository.findAll();
        List<PhysicianResponseDTO> physicianResponseDTOS = new ArrayList<>();
        for (PhysicianEntity physicianEntity : doctorEntities) {
            physicianResponseDTOS.add(getDoctorResponseDTO(physicianEntity));
        }
        return physicianResponseDTOS;
    }

    //                      fixme -   -   -   -   -   -   -   -   -   -   -   -


    private PhysicianResponseDTO getDoctorResponseDTO(PhysicianEntity physicianEntity) {
        PhysicianResponseDTO.builder().build();
        return PhysicianResponseDTO.builder()
                .id(physicianEntity.getId())
                .firstName(physicianEntity.getFirstName())
                .lastName(physicianEntity.getLastName())
                .physiciansSpecialization(physicianEntity.getPhysicianSpecialization())
                .build();
    }
}
