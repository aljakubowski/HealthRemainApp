package com.alja.visit.service;

import com.alja.patient.client.PatientClient;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.PhysicianResponseDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientsService {

    private final PhysicianClient physicianClient;
    private final PatientClient patientClient;
    private final String DATA_FORMAT = "DETAILS";

    public PhysicianResponseDTO getPhysicianResponseDTO(String physicianId) {
        return physicianClient.getPhysicianById(physicianId, false);
    }

    public PatientResponseDTO getPatientResponseDTO(String patientId) {
        if (StringUtils.isBlank(patientId)) {
            return null;
        }
        return patientClient.getPatientById(patientId, DATA_FORMAT);
    }
}
