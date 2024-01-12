package com.alja.visit.service;

import com.alja.patient.client.PatientClient;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClientsService {

    private final PhysicianClient physicianClient;
    private final PatientClient patientClient;
    private final String DATA_FORMAT = "DETAILS";
    private final boolean NO_DETAILS = false;

    public PhysicianResponseDTO getPhysicianResponseDTO(String physicianId) {
        PhysicianResponseDTO physicianResponse;
        try {
            physicianResponse = physicianClient.getPhysicianById(physicianId, NO_DETAILS);
        } catch (RuntimeException e) {
            return PhysicianResponseDTO.builder().build();
        }
        return physicianResponse;
    }

    public PatientResponseDTO getPatientResponseDTO(String patientId) {
        if (StringUtils.isBlank(patientId)) {
            return null;
        }
        return patientClient.getPatientById(patientId, DATA_FORMAT);
    }

    public List<String> getAvailableSpecializations() {
        return physicianClient.getAllSpecializations().stream()
                .map(PhysicianSpecializationDTO::getSpecializationName)
                .collect(Collectors.toList());
    }
}
