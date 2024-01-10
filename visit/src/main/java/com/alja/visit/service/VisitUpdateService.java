package com.alja.visit.service;

import com.alja.common.enums.VisitStatus;
import com.alja.errors.VisitError;
import com.alja.exception.VisitException;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.VisitUpdateDTO;
import com.alja.visit.model.VisitEntity;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Service
public class VisitUpdateService {

    private final ClientsService clientsService;
    private final VisitValidationService visitValidationService;

    public VisitEntity updateVisit(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {

        if (Objects.nonNull(visitUpdateDTO.getVisitDate())
                && visitToUpdate.getVisitStartDate() != visitUpdateDTO.getVisitDate()) {
            validateAndUpdateVisitDate(visitToUpdate, visitUpdateDTO);
        }

        if (StringUtils.isNotBlank(visitUpdateDTO.getPhysicianId())
                && !visitToUpdate.getPhysicianId().equals(visitUpdateDTO.getPhysicianId())) {
            visitValidationService.validatePhysicianAvailability(visitUpdateDTO.getPhysicianId(),
                    visitToUpdate.getVisitStartDate());
            updatePhysician(visitToUpdate, visitUpdateDTO);
        }

        if (StringUtils.isNotBlank(visitUpdateDTO.getPatientId())
                && !visitToUpdate.getPatientId().equals(visitUpdateDTO.getPatientId())) {
            visitValidationService.validatePatientAvailability(visitUpdateDTO.getPatientId(),
                    visitToUpdate.getVisitStartDate());
            updatePatient(visitToUpdate, visitUpdateDTO);
        }

        if (StringUtils.isNotBlank(visitUpdateDTO.getVisitStatus())
        && !visitToUpdate.getVisitStatus().name().equalsIgnoreCase(visitUpdateDTO.getVisitStatus())) {
            updateVisitStatus(visitToUpdate, getStatus(visitUpdateDTO.getVisitStatus()));
        }

        if (Objects.nonNull(visitUpdateDTO.getPhysicianRecommendations())
                && !visitUpdateDTO.getPhysicianRecommendations().isEmpty()) {
            visitToUpdate.updatePhysicianRecommendations(visitUpdateDTO.getPhysicianRecommendations());
        }
        return visitToUpdate;
    }

    private void validateAndUpdateVisitDate(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        if (Objects.nonNull(visitUpdateDTO.getPhysicianId())) {
            if (Objects.nonNull(visitUpdateDTO.getPatientId())) {
                updateVisitDateAndPhysicianAndPatient(visitToUpdate, visitUpdateDTO);
            } else {
                updateVisitDateAndPhysician(visitToUpdate, visitUpdateDTO);
            }
        } else {
            if (Objects.nonNull(visitUpdateDTO.getPatientId())) {
                updateVisitDateAndPatient(visitToUpdate, visitUpdateDTO);
            }
        }
        updateVisitDate(visitToUpdate, visitUpdateDTO);
    }

    private void updateVisitDateAndPhysicianAndPatient(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        validateAvailabilityStatusAndUpdate(visitToUpdate, visitUpdateDTO);
        updatePhysician(visitToUpdate, visitUpdateDTO);
        visitToUpdate.updatePatientId(visitUpdateDTO.getPatientId());
    }

    private void updateVisitDateAndPhysician(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        validateAvailabilityStatusAndUpdate(visitToUpdate, visitUpdateDTO);
        updatePhysician(visitToUpdate, visitUpdateDTO);
    }

    private void updateVisitDateAndPatient(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        validateAvailabilityStatusAndUpdate(visitToUpdate, visitUpdateDTO);
        updatePatient(visitToUpdate, visitUpdateDTO);
    }

    private void updateVisitDate(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        validateAvailabilityStatusAndUpdate(visitToUpdate, visitUpdateDTO);
    }

    private void updatePhysician(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        PhysicianResponseDTO updatedPhysician = clientsService.getPhysicianResponseDTO(visitUpdateDTO.getPhysicianId());
        visitToUpdate.updatePhysicianId(updatedPhysician.getPhysicianId());
        visitToUpdate.updatePhysicianSpecialization(updatedPhysician.getPhysiciansSpecialization());
    }

    private void updatePatient(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        visitToUpdate.updatePatientId(visitUpdateDTO.getPatientId());
    }

    private void validateAvailabilityStatusAndUpdate(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO) {
        visitValidationService.validatePhysicianAvailability(visitUpdateDTO.getPhysicianId(), visitUpdateDTO.getVisitDate());
        visitValidationService.validatePatientAvailability(visitUpdateDTO.getPatientId(), visitUpdateDTO.getVisitDate());

        LocalDateTime visitDateUpdated = visitUpdateDTO.getVisitDate();
        LocalDateTime visitEndDate = visitValidationService.getVisitEndDate(visitDateUpdated);
        validateVisitDateWithStatus(visitToUpdate, visitUpdateDTO, visitEndDate);

        visitToUpdate.updateVisitDates(visitDateUpdated, visitEndDate);
    }

    private void validateVisitDateWithStatus(VisitEntity visitToUpdate, VisitUpdateDTO visitUpdateDTO,
                                             LocalDateTime visitEndDate) {
        LocalDateTime visitDateUpdated = visitUpdateDTO.getVisitDate();
        if (statusToBeUpdated(visitUpdateDTO.getVisitStatus())) {
            visitValidationService.validateDateWithStatus(getStatus(visitUpdateDTO.getVisitStatus()), visitDateUpdated, visitEndDate);
        } else {
            visitValidationService.validateDateWithStatus(visitToUpdate.getVisitStatus(), visitDateUpdated, visitEndDate);
        }
        visitValidationService.validateDateRange(visitDateUpdated, visitEndDate);
    }

    private boolean statusToBeUpdated(String visitStatus) {
        return StringUtils.isNotBlank(visitStatus);
    }

    private void updateVisitStatus(VisitEntity visitToUpdate, VisitStatus visitStatus) {
        if (StringUtils.isNotBlank(visitToUpdate.getPatientId()) && visitStatus == VisitStatus.AVAILABLE) {
            throw new VisitException(VisitError.VISIT_INVALID_AVAILABLE_STATUS_ERROR);
        }
        visitValidationService.validateDateWithStatus(visitStatus,
                visitToUpdate.getVisitStartDate(), visitToUpdate.getVisitEndDate());
        visitToUpdate.updateVisitStatus(visitStatus);
    }

    private VisitStatus getStatus(String status) {
        return VisitStatus.valueOf(status.toUpperCase());
    }

}
