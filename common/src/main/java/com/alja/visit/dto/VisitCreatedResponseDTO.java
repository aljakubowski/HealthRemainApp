package com.alja.visit.dto;

import com.alja.common.enums.VisitStatus;
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
public class VisitCreatedResponseDTO {
    private PhysicianResponseDTO physicianResponseDTO;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private VisitStatus visitStatus;
}
