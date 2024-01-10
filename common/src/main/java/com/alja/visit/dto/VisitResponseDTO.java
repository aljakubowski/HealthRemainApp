package com.alja.visit.dto;

import com.alja.common.enums.VisitStatus;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class VisitResponseDTO {
    private String visitId;
    private PhysicianResponseDTO physicianResponseDTO;
    private PatientResponseDTO patientResponseDTO;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private VisitStatus visitStatus;
    private List<String> physicianRecommendations;
}
