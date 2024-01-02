package com.alja.visit.dto;

import com.alja.common.enums.VisitStatus;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class VisitResponseDTO {
    private PhysicianResponseDTO physicianResponseDTO;
    private PatientResponseDTO patientResponseDTO;
    private LocalDateTime visitDate;
    private VisitStatus visitStatus;
}
