package com.alja.visit.service;

import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.VisitCreatedResponseDTO;
import com.alja.visit.dto.VisitFilterDTO;
import com.alja.visit.dto.VisitNewDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.model.VisitEntity;
import com.alja.visit.model.mapper.VisitMapper;
import com.alja.visit.model.repository.VisitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.alja.visit.VisitLogs.ADD_VISIT;
import static com.alja.visit.VisitLogs.GET_ALL_VISITS;

@Slf4j
@AllArgsConstructor
@Service
public class VisitAdminService {

    private final VisitValidationService visitValidationService;
    private final PhysicianClient physicianClient;
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final LogService logService;
    private final static Integer VISIT_STANDARD_LENGTH = 30;

    //todo unit int test

    public VisitCreatedResponseDTO addNewVisit(VisitNewDTO visitNewDTO) {
        logService.logOperation(ADD_VISIT.logMessage, visitNewDTO.getVisitDate().toString());
        PhysicianResponseDTO physicianResponseDTO = getAndValidatePhysician(visitNewDTO);
        VisitEntity visitEntity = visitMapper.toVisitEntity(visitNewDTO,
                physicianResponseDTO.getPhysiciansSpecialization(),
                getVisitEndDate(visitNewDTO.getVisitDate()));
        visitRepository.save(visitEntity);
        return getVisitResponse(visitEntity);
    }


    public List<VisitCreatedResponseDTO> getAllAvailableVisit() {
        logService.logOperation(GET_ALL_VISITS.logMessage);
        List<VisitEntity> visits = visitRepository.findAll();
        return visits.stream().map(this::getVisitResponse).toList();
    }

    public List<VisitResponseDTO> getVisitsWithFilter(VisitFilterDTO visitFilter) {

        visitRepository.findAll();
        return null;
    }

    private PhysicianResponseDTO getAndValidatePhysician(VisitNewDTO visitNewDTO){
        String physicianId = visitNewDTO.getPhysicianId();
        PhysicianResponseDTO physicianResponseDTO = getPhysicianResponseDTO(physicianId);
        visitValidationService.validatePhysicianAvailability(physicianId, visitNewDTO.getVisitDate());
        return physicianResponseDTO;
    }

    private LocalDateTime getVisitEndDate(LocalDateTime visitStartDate) {
        return visitStartDate.plusMinutes(VISIT_STANDARD_LENGTH);
    }

    private VisitCreatedResponseDTO getVisitResponse(VisitEntity visitEntity) {
        return VisitCreatedResponseDTO.builder()
                .physicianResponseDTO(getPhysicianResponseDTO(visitEntity.getPhysicianId()))
                .visitStartDate(visitEntity.getVisitStartDate())
                .visitEndDate(visitEntity.getVisitEndDate())
                .visitStatus(visitEntity.getVisitStatus())
                .build();
    }

    private PhysicianResponseDTO getPhysicianResponseDTO(String physicianId){
        return physicianClient.getPhysicianById(physicianId, false);
    }
}
