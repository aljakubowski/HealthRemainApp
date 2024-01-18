package com.alja.patient.controller_resource;

import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.patient.dto.PatientUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "admin api", description = "patient managing data")
public interface PatientAdminApiDoc {


    @Operation(summary = "Patient create")
    ResponseEntity<PatientResponseDTO> registerNewPatient(PatientRegisterDTO patientRegisterDTO);


    @Operation(summary = "Patients show all")
    ResponseEntity<List<PatientResponseDTO>> getAllPatients();

    @Operation(summary = "Patients show info",
            parameters = {
                    @Parameter(name = "patientId", required = true),
                    @Parameter(name = "dataFormat", required = true, description = "choose data format of patient info: details or visits")})
    ResponseEntity<PatientResponseDTO> getPatientById(String patientId, String dataFormat);


    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Patient update info")
    ResponseEntity<PatientResponseDTO> updatePatient(String patientId, PatientUpdateDTO patientUpdateDTO);


    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Patient delete")
    ResponseEntity<PatientResponseDTO> deletePatientById(String patientId);

}
