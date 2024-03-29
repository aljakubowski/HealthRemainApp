package com.alja.visit.service;

import com.alja.visit.dto.VisitCheckResponseDTO;
import com.alja.visit.dto.VisitCommonFilterDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import com.alja.visit.model.VisitEntity;
import com.alja.visit.model.repository.VisitRepository;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alja.visit.VisitLogs.*;

@Slf4j
@AllArgsConstructor
@Service
public class VisitPatientService {

    private final VisitValidationService visitValidationService;
    private final VisitQueryPredicateService visitQueryPredicateService;
    private final VisitUpdateService visitUpdateService;
    private final VisitResponseService visitResponseService;
    private final VisitRepository visitRepository;
    private final VisitSorter visitSorter;
    private final LogService logService;

    public List<VisitSimpleResponseDTO> getAllPatientVisits(String patientId) {
        logService.logOperation(GET_ALL_PATIENT_VISITS.logMessage, patientId);

        visitValidationService.validateIfPatientPresent(patientId);
        List<VisitEntity> visits = visitRepository.findAllByPatientId(patientId);
        visitSorter.sortVisitsDefault(visits);
        return visits.stream().map(this::getVisitSimpleResponse).toList();
    }

    public List<VisitSimpleResponseDTO> searchAvailableVisitsWithFilter(VisitCommonFilterDTO visitFilter) {
        logService.logOperation(GET_VISITS_FILTERED.logMessage);

        visitFilter = getValidatedVisitFilter(visitFilter);
        Predicate predicate = visitQueryPredicateService.getPredicatePatientVisits(visitFilter);
        Sort sort = visitSorter.getDefaultSort();
        List<VisitEntity> visits = IterableUtils.toList(visitRepository.findAll(predicate, sort));
        return visits.stream().map(this::getVisitSimpleResponse).toList();
    }

    public VisitResponseDTO getVisitById(String patientId, String visitId) {
        logService.logOperation(GET_VISIT.logMessage, visitId);

        visitValidationService.validateIfPatientPresent(patientId);
        VisitEntity visitEntity = visitValidationService.findVisitIfPresent(visitId);
        visitValidationService.validateIfPatientsVisit(patientId, visitEntity.getPatientId());
        return getVisitResponse(visitEntity);
    }

    public VisitSimpleResponseDTO makeVisitAppointment(String patientId, String visitId) {
        logService.logOperation(APPOINTING_VISIT.logMessage, patientId, visitId);

        visitValidationService.validateIfPatientPresent(patientId);
        VisitEntity visitEntity = visitValidationService.findVisitIfPresent(visitId);
        visitValidationService.validateIfVisitAvailable(visitEntity.getPatientId(), visitEntity.getVisitStatus());

        visitUpdateService.appointVisit(visitEntity, patientId);
        visitRepository.save(visitEntity);
        return getVisitSimpleResponse(visitEntity);
    }

    public VisitSimpleResponseDTO cancelVisitAppointment(String patientId, String visitId) {
        logService.logOperation(CANCELING_VISIT.logMessage, patientId, visitId);

        visitValidationService.validateIfPatientPresent(patientId);
        VisitEntity visitEntity = visitValidationService.findVisitIfPresent(visitId);
        visitValidationService.validateIfPatientsVisit(patientId, visitEntity.getPatientId());

        visitUpdateService.cancelVisit(visitEntity);
        visitRepository.save(visitEntity);
        return getVisitSimpleResponse(visitEntity);
    }

    public VisitCheckResponseDTO checkVisit(String checkId) {
        boolean hasVisits = true;
        if (visitRepository.findVisitEntitiesByPhysicianId(checkId).isEmpty()) {
            hasVisits = false;
        }
        return visitResponseService.getCheckResponse(hasVisits);
    }

    private VisitSimpleResponseDTO getVisitSimpleResponse(VisitEntity visitEntity) {
        return visitResponseService.getVisitSimpleResponse(visitEntity);
    }

    private VisitResponseDTO getVisitResponse(VisitEntity visitEntity) {
        return visitResponseService.getVisitResponse(visitEntity);
    }

    private VisitCommonFilterDTO getValidatedVisitFilter(VisitCommonFilterDTO visitFilter) {
        if (visitFilter != null) {
            visitValidationService.validateDates(visitFilter);
            visitValidationService.validatePhysicianIdAndSpecialization(visitFilter);
        } else {
            visitFilter = VisitCommonFilterDTO.builder().build();
        }
        return visitFilter;
    }

}
