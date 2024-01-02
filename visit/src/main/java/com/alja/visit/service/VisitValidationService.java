package com.alja.visit.service;

import com.alja.errors.VisitError;
import com.alja.exception.VisitException;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.model.repository.VisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class VisitValidationService {

    private final VisitRepository visitRepository;

    public void validatePhysicianAvailability(String physicianId, LocalDateTime visitDate) {
        visitRepository.getVisitEntitiesByPhysicianId(physicianId)
                .forEach(visitEntity -> checkIfVisitCoincide(visitDate,
                        visitEntity.getVisitStartDate(),
                        visitEntity.getVisitEndDate()));
    }

    private void checkIfVisitCoincide(LocalDateTime visitDate, LocalDateTime visitDateStart, LocalDateTime visitDateEnd) {
        if (visitDate.isAfter(visitDateStart) && visitDate.isBefore(visitDateEnd)) {
            throw new VisitException(VisitError.VISIT_COINCIDE_ERROR);
        }
    }
}
