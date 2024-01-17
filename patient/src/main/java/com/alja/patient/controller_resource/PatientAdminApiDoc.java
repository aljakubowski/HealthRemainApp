package com.alja.patient.controller_resource;

import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "patient", description = "Admin API for patient control")
public interface PatientAdminApiDoc {


    @Operation(summary = "Admin patient register")
    ResponseEntity<PatientResponseDTO> registerNewPatient(PatientRegisterDTO patientRegisterDTO);


    @Operation(summary = "Admin patients show all")
    ResponseEntity<List<PatientResponseDTO>> getAllPatients();

    @Operation(summary = "Admin patients show info",
            parameters = {
                    @Parameter(name = "patientId", required = true),
                    @Parameter(name = "dataFormat", required = true, description = "choose data format of patient info: details or visits")})
    ResponseEntity<PatientResponseDTO> getPatientById(String patientId, String dataFormat);


    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Admin patient update info")
    ResponseEntity<PatientResponseDTO> updatePatient(String patientId, PatientUpdateDTO patientUpdateDTO);


    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Admin patient deregister")
    ResponseEntity<PatientResponseDTO> deletePatientById(String patientId);

}
