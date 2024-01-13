package com.alja.visit.service;

import com.alja.common.enums.VisitStatus;
import com.alja.errors.VisitError;
import com.alja.exception.VisitException;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.VisitCommonFilterDTO;
import com.alja.visit.model.VisitEntity;
import com.alja.visit.model.repository.VisitRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VisitValidationService {

    private final VisitRepository visitRepository;
    private final ClientsService clientsService;

    private final static Integer VISIT_STANDARD_LENGTH = 30;

    public LocalDateTime getVisitEndDate(LocalDateTime visitStartDate) {
        return visitStartDate.plusMinutes(VISIT_STANDARD_LENGTH);
    }


    public void validateDateWithStatus(VisitStatus visitStatus, LocalDateTime visitDateFrom, LocalDateTime visitDateTo) {
        if (isAvailableOrReserved(visitStatus)) {
            checkIfFutureDate(visitDateFrom);
            checkIfFutureDate(visitDateTo);
        }
        if (isCompletedOrUnrealized(visitStatus)) {
            checkIfPastDate(visitDateFrom);
            checkIfPastDate(visitDateTo);
        }
    }

    public void validatePhysicianAvailability(String physicianId, LocalDateTime visitDateToCheck) {
        validateIfPhysicianPresent(physicianId);
        visitRepository.findVisitEntitiesByPhysicianId(physicianId)
                .forEach(visitEntity -> checkIfVisitCoincide(visitDateToCheck,
                        visitEntity.getVisitStartDate(),
                        visitEntity.getVisitEndDate()));
    }

    public void validatePatientAvailability(String patientId, LocalDateTime visitDate) {
        visitRepository.findVisitEntitiesByPatientId(patientId)
                .forEach(visitEntity -> checkIfVisitCoincide(visitDate,
                        visitEntity.getVisitStartDate(),
                        visitEntity.getVisitEndDate()));
    }

    public VisitEntity findVisitIfPresent(String visitId) {
        return visitRepository.findVisitEntityByVisitId(visitId)
                .orElseThrow(() -> new VisitException(VisitError.VISIT_NOT_FOUND_ERROR));
    }

    public void validateStatus(String visitStatus) {
        if (visitStatus == null) {
            return;
        }
        if (!VisitStatus.isValid(visitStatus)) {
            List<String> availableStatuses = getVisitStatusList();
            throw new VisitException(VisitError.VISIT_INVALID_STATUS_ERROR, availableStatuses.toString());
        }
    }


    public void validateDates(VisitCommonFilterDTO visitFilter) {
        LocalDateTime visitDateFrom = visitFilter.getVisitDateFrom();
        LocalDateTime visitDateTo = visitFilter.getVisitDateTo();

        if (visitDateFrom == null && visitDateTo == null) {
            return;
        }
        if (visitDateFrom != null && visitDateTo != null) {
            validateDateRange(visitDateFrom, visitDateTo);
        }
    }

    public void validateDateRange(LocalDateTime visitDateFrom, LocalDateTime visitDateTo) {
        if (visitDateFrom.isAfter(visitDateTo)) {
            throw new VisitException(VisitError.VISIT_DATE_INVALID_RANGE_ERROR);
        }
    }

    public void validatePhysicianIdAndSpecialization(VisitCommonFilterDTO visitFilter) {
        if (StringUtils.isNotBlank(visitFilter.getPhysicianId())
                && StringUtils.isNotBlank(visitFilter.getPhysicianSpecialization())) {
            throw new VisitException(VisitError.VISIT_INVALID_SEARCH_ERROR);
        }
        if (StringUtils.isNotBlank(visitFilter.getPhysicianSpecialization())) {
            validateAvailablePhysicianSpecializations(visitFilter.getPhysicianSpecialization());
        }
    }

    private void validateAvailablePhysicianSpecializations(String physicianSpecialization) {
        List<String> physicianSpecializations = clientsService.getAvailableSpecializations();
        if (physicianSpecializations.stream().noneMatch(p -> p.equalsIgnoreCase(physicianSpecialization))) {
            throw new VisitException(VisitError.VISIT_INVALID_PHYSICIAN_SPECIALIZATION_ERROR,
                    physicianSpecializations.toString());
        }
    }

    private void validateIfPhysicianPresent(String physicianId) {
        PhysicianResponseDTO physicianResponse;
        try {
            physicianResponse = clientsService.getPhysicianResponseDTO(physicianId);
        } catch (FeignException e) {
            throw new VisitException(VisitError.VISIT_INVALID_PHYSICIAN_ERROR);
        }
        if (physicianResponse == null || StringUtils.isBlank(physicianResponse.getPhysicianId())) {
            throw new VisitException(VisitError.VISIT_INVALID_PHYSICIAN_ERROR);
        }
    }

    public void validateIfPatientPresent(String patientId) {
        PatientResponseDTO patientResponse;
        try {
            patientResponse = clientsService.getPatientResponseDTO(patientId);
        } catch (RuntimeException e) {
            throw new VisitException(VisitError.VISIT_INVALID_PATIENT_ERROR);
        }
        validateIfEmptyId(patientResponse.getPatientId());
    }

    public void validateIfReserved(VisitStatus visitStatus) {
        if (visitStatus == VisitStatus.RESERVED) {
            throw new VisitException(VisitError.VISIT_RESERVED_ERROR);
        }
    }

    public void validateIfPatientsVisit(String patientId, String visitPatientId) {
        if (!patientId.equals(visitPatientId)) {
            throw new VisitException(VisitError.VISIT_INVALID_ID_ERROR);
        }
    }

    public void validateIfVisitAvailable(String visitPatientId, VisitStatus visitStatus) {
        if (StringUtils.isNotBlank(visitPatientId) || !visitStatus.equals(VisitStatus.AVAILABLE)) {
            throw new VisitException(VisitError.VISIT_NOT_AVAILABLE_ERROR);
        }
    }

    private boolean isAvailableOrReserved(VisitStatus visitStatus) {
        return visitStatus.equals(VisitStatus.AVAILABLE) || visitStatus.equals(VisitStatus.RESERVED);
    }


    private boolean isCompletedOrUnrealized(VisitStatus visitStatus) {
        return visitStatus.equals(VisitStatus.COMPLETED) || visitStatus.equals(VisitStatus.UNREALIZED);
    }


    private void checkIfFutureDate(LocalDateTime futureDate) {
        if (futureDate == null) {
            return;
        }
        if (futureDate.isBefore(LocalDateTime.now())) {
            throw new VisitException(VisitError.VISIT_PAST_DATE_INVALID_ERROR);
        }
    }

    private void checkIfPastDate(LocalDateTime pastDate) {
        if (pastDate == null) {
            return;
        }
        if (pastDate.isAfter(LocalDateTime.now())) {
            throw new VisitException(VisitError.VISIT_FUTURE_DATE_INVALID_ERROR);
        }
    }

    private void checkIfVisitCoincide(LocalDateTime requestedVisitDate, LocalDateTime visitDateStart, LocalDateTime visitDateEnd) {

        if (dateIsAfterOrEqual(requestedVisitDate, visitDateStart)
                && dateIsBeforeOrEqual(requestedVisitDate, visitDateEnd)) {
            throw new VisitException(VisitError.VISIT_COINCIDE_ERROR);
        }

        LocalDateTime requestedVisitDateEnd = getVisitEndDate(requestedVisitDate);

        if (dateIsAfterOrEqual(requestedVisitDateEnd, visitDateStart)
                && dateIsBeforeOrEqual(requestedVisitDateEnd, visitDateEnd)) {
            throw new VisitException(VisitError.VISIT_COINCIDE_ERROR);
        }
    }

    private void validateIfEmptyId(String patientId) {
        if (StringUtils.isBlank(patientId)) {
            throw new VisitException(VisitError.VISIT_INVALID_PATIENT_ERROR);
        }
    }

    private boolean dateIsAfterOrEqual(LocalDateTime requestedVisitDate, LocalDateTime visitDateStart) {
        return !requestedVisitDate.isBefore(visitDateStart);
    }

    private boolean dateIsBeforeOrEqual(LocalDateTime requestedVisitDate, LocalDateTime visitDateStart) {
        return !requestedVisitDate.isAfter(visitDateStart);
    }

    private List<String> getVisitStatusList() {
        return Arrays.stream(VisitStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
