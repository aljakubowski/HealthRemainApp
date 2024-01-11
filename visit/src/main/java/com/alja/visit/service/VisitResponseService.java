package com.alja.visit.service;

import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import com.alja.visit.model.VisitEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VisitResponseService {

    private final ClientsService clientsService;

    public VisitSimpleResponseDTO getVisitSimpleResponse(VisitEntity visitEntity) {
        return VisitSimpleResponseDTO.builder()
                .visitId(visitEntity.getVisitId())
                .physicianResponseDTO(clientsService.getPhysicianResponseDTO(visitEntity.getPhysicianId()))
                .visitStartDate(visitEntity.getVisitStartDate())
                .visitEndDate(visitEntity.getVisitEndDate())
                .visitStatus(visitEntity.getVisitStatus())
                .build();
    }

    public VisitResponseDTO getVisitResponse(VisitEntity visitEntity) {
        return VisitResponseDTO.builder()
                .visitId(visitEntity.getVisitId())
                .physicianResponseDTO(clientsService.getPhysicianResponseDTO(visitEntity.getPhysicianId()))
                .patientResponseDTO(getPatientResponseIfRegisteredVisit(visitEntity.getPatientId()))
                .visitStartDate(visitEntity.getVisitStartDate())
                .visitEndDate(visitEntity.getVisitEndDate())
                .visitStatus(visitEntity.getVisitStatus())
                .physicianRecommendations(visitEntity.getPhysicianRecommendations())
                .build();
    }

    public PhysicianResponseDTO getPhysicianResponse(String physicianId) {
        return clientsService.getPhysicianResponseDTO(physicianId);
    }

    private PatientResponseDTO getPatientResponseIfRegisteredVisit(String patientId) {
        return clientsService.getPatientResponseDTO(patientId);
    }

}
