package com.alja.visit.service;

import com.alja.common.enums.VisitStatus;
import com.alja.errors.VisitError;
import com.alja.exception.VisitException;
import com.alja.visit.dto.VisitFilterDTO;
import com.alja.visit.model.VisitEntity;
import com.alja.visit.model.repository.VisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class VisitValidationService {

    private final VisitRepository visitRepository;

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
        visitRepository.findVisitEntitiesByPhysicianId(physicianId)
                .forEach(visitEntity -> checkIfVisitCoincide(visitDateToCheck,
                        visitEntity.getVisitStartDate(),
                        visitEntity.getVisitEndDate()));
    }

    public void validatePatientAvailability(String patientId, LocalDateTime visitDate) {
        System.out.println();
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
            throw new VisitException(VisitError.VISIT_INVALID_STATUS_ERROR);
        }
    }

    public void validateDates(VisitFilterDTO visitFilter) {
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

    private boolean dateIsAfterOrEqual(LocalDateTime requestedVisitDate, LocalDateTime visitDateStart) {
        return !requestedVisitDate.isBefore(visitDateStart);
    }

    private boolean dateIsBeforeOrEqual(LocalDateTime requestedVisitDate, LocalDateTime visitDateStart) {
        return !requestedVisitDate.isAfter(visitDateStart);
    }
}
