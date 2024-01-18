package com.alja.patient.controller_resource;

import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "patient api", description = "patient registering and managing data")
public interface PatientApiDoc {

    @Operation(summary = "Patient self register")
    ResponseEntity<PatientResponseDTO> registerNewPatientByPatient(PatientRegisterDTO patientRegisterDTODTO);

    @Operation(summary = "Patient show info",
            parameters = {
                    @Parameter(name = "patientId", required = true),
                    @Parameter(name = "dataFormat", description = "choose data format of patient info: details or visits")})
    ResponseEntity<PatientResponseDTO> getPatientByIdByPatient(String patientId, String dataFormat);

    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Patient self update info")
    ResponseEntity<PatientResponseDTO> updatePatientByPatient(String patientId, PatientUpdateDTO patientUpdateDTO);

    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Patient self deregister")
    ResponseEntity<PatientResponseDTO> deletePatientByIdByPatient(String patientId);

}
