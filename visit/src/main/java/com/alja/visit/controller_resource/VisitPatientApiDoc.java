package com.alja.visit.controller_resource;

import com.alja.visit.dto.VisitCheckResponseDTO;
import com.alja.visit.dto.VisitCommonFilterDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.dto.VisitSimpleResponseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "patient api", description = "appointing and managing visits")
public interface VisitPatientApiDoc {


    @Parameter(name = "patientId", required = true)
    @Operation(summary = "Visits get all reserved")
    List<VisitSimpleResponseDTO> getAllPatientVisits(String patientId);


    @Operation(summary = "Visits get all available with filter")
    List<VisitSimpleResponseDTO> searchAvailableVisitsWithFilter(VisitCommonFilterDTO visitCommonFilterDTO);


    @Operation(summary = "Visit get by id",
            parameters = {
                    @Parameter(name = "patientId", required = true),
                    @Parameter(name = "visitId", required = true)})
    VisitResponseDTO getVisitById(String patientId, String visitId);


    @Operation(summary = "Visit appoint",
            parameters = {
                    @Parameter(name = "patientId", required = true),
                    @Parameter(name = "visitId", required = true)})
    VisitSimpleResponseDTO makeVisitAppointment(String patientId, String visitId);


    @Operation(summary = "Visit cancel",
            parameters = {
                    @Parameter(name = "patientId", required = true),
                    @Parameter(name = "visitId", required = true)})
    VisitSimpleResponseDTO cancelVisitAppointment(String patientId, String visitId);


    @Hidden
    VisitCheckResponseDTO checkVisits(String checkId);
}
