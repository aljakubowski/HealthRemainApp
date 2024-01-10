package com.alja.visit.service;

import com.alja.common.client.PatientClient;
import com.alja.common.client.PhysicianClient;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientsService {

    private final PhysicianClient physicianClient;
    private final PatientClient patientClient;

    public PhysicianResponseDTO getPhysicianResponseDTO(String physicianId) {
        return physicianClient.getPhysicianById(physicianId, false);
    }

    public PatientResponseDTO getPatientResponseDTO(String patientId) {
        return patientClient.getPatientById(patientId);
    }
}
