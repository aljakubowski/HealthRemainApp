package com.alja.patient.service;

import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientResponseDetailedDTO;
import com.alja.patient.dto.PatientResponseVisitsDTO;
import com.alja.patient.model.PatientEntity;
import com.alja.patient.model.mapper.PatientMapper;
import com.alja.visit.client.VisitClient;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Service
public class PatientResponseService {

    private final PatientDataValidationService patientDataValidationService;
    private final PatientMapper patientMapper;
    private final VisitClient visitClient;
    private final String DATA_FORMAT_DETAILS = "DETAILS";
    private final String DATA_FORMAT_VISITS = "VISITS";
    private final String DATA_FORMAT_EMPTY = "";


    public PatientResponseDTO returnAppropriateResponse(PatientEntity patientEntity, String dataFormat) {
        dataFormat = validateDataFormat(dataFormat);
        switch (dataFormat) {
            case DATA_FORMAT_DETAILS:
                return getPatientResponseDetailed(patientEntity);
            case DATA_FORMAT_VISITS:
                return getPatientResponseWithVisits(patientEntity);
            default:
                return getPatientResponseSimple(patientEntity);
        }
    }

    public PatientResponseDTO getPatientResponseSimple(PatientEntity patientEntity) {
        return PatientResponseDTO.builder()
                .patientId(patientEntity.getPatientId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .build();
    }

    public PatientResponseDetailedDTO getPatientResponseDetailed(PatientEntity patientEntity) {
        return PatientResponseDetailedDTO.builder()
                .patientId(patientEntity.getPatientId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .birthDate(patientEntity.getBirthDate().toString())
                .age(patientDataValidationService.calculateAge(patientEntity.getBirthDate().toString()))
                .socialSecurityNumber(patientEntity.getSocialSecurityNumber())
                .contactDetails(patientMapper.contactDetailsToDto(patientEntity.getContactDetails()))
                .address(patientMapper.addressToDto(patientEntity.getAddress()))
                .registrationDate(patientEntity.getRegistrationDate())
                .build();
    }

    public PatientResponseVisitsDTO getPatientResponseWithVisits(PatientEntity patientEntity) {
        List<String> visits = getVisitsIds(patientEntity.getPatientId());
        return PatientResponseVisitsDTO.builder()
                .patientId(patientEntity.getPatientId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .visitsId(visits)
                .build();
    }

    private List<String> getVisitsIds(String patientId) {
        return visitClient.getAllPatientVisits(patientId).stream()
                .map(VisitSimpleResponseDTO::getVisitId)
                .collect(Collectors.toList());
    }

    private String validateDataFormat(String dataFormat) {
        return StringUtils.isBlank(dataFormat) ?
                DATA_FORMAT_EMPTY
                : dataFormat;
    }

}
