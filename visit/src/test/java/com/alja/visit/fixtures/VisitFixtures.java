package com.alja.visit.fixtures;

import com.alja.common.enums.VisitStatus;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.visit.dto.*;
import com.alja.visit.model.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public class VisitFixtures {

    public static VisitNewDTO createVisitNewDTO(String physicianId, LocalDateTime visitDate) {
        return VisitNewDTO.builder()
                .physicianId(physicianId)
                .visitDate(visitDate)
                .build();
    }

    public static VisitFilterDTO createVisitFilterWithStatusAndDatesAndId(String visitStatus,
                                                                          LocalDateTime visitDateFrom,
                                                                          LocalDateTime visitDateTo) {
        return VisitFilterDTO.builder()
                .patientId("id")
                .visitStatus(visitStatus)
                .visitDateFrom(visitDateFrom)
                .visitDateTo(visitDateTo)
                .build();
    }

    public static VisitFilterDTO createVisitFilterWithStatusAndDates(String visitStatus,
                                                                     LocalDateTime visitDateFrom,
                                                                     LocalDateTime visitDateTo) {
        return VisitFilterDTO.builder()
                .visitStatus(visitStatus)
                .visitDateFrom(visitDateFrom)
                .visitDateTo(visitDateTo)
                .build();
    }

    public static VisitFilterDTO createVisitFilterWithPhysicianIdAndSpecialization(String physicianId, String spec) {
        return VisitFilterDTO.builder()
                .physicianId(physicianId)
                .physicianSpecialization(spec)
                .build();
    }

    public static VisitFilterDTO createVisitFilterWithAllFields(String physicianId,
                                                                String physicianSpecialization,
                                                                String patientId,
                                                                String visitStatus,
                                                                LocalDateTime visitDateFrom,
                                                                LocalDateTime visitDateTo) {
        return VisitFilterDTO.builder()
                .physicianId(physicianId)
                .physicianSpecialization(physicianSpecialization)
                .patientId(patientId)
                .visitStatus(visitStatus)
                .visitDateFrom(visitDateFrom)
                .visitDateTo(visitDateTo)
                .build();
    }

    public static VisitFilterDTO createVisitFilterWithSpecializationAndStatusAndDateFrom(String physicianSpecialization,
                                                                                         String visitStatus,
                                                                                         LocalDateTime visitDateFrom) {
        return VisitFilterDTO.builder()
                .physicianSpecialization(physicianSpecialization)
                .visitStatus(visitStatus)
                .visitDateFrom(visitDateFrom)
                .build();
    }

    public static VisitCommonFilterDTO createVisitCommonFilter(String physicianSpecialization,
                                                               LocalDateTime visitDateFrom,
                                                               LocalDateTime visitDateTo) {
        return VisitFilterDTO.builder()
                .physicianSpecialization(physicianSpecialization)
                .visitStatus(VisitStatus.AVAILABLE.name())
                .visitDateFrom(visitDateFrom)
                .visitDateTo(visitDateTo)
                .build();
    }

    public static VisitCommonFilterDTO createVisitCommonFilterWithStatusAvailable() {
        return VisitFilterDTO.builder()
                .visitStatus(VisitStatus.AVAILABLE.name())
                .build();
    }

    public static VisitEntity createVisitEntityWithPatientIdAndStatus(String id, VisitStatus status) {
        return VisitEntity.builder()
                .patientId(id)
                .visitStatus(status)
                .visitEndDate(LocalDateTime.now())
                .visitStartDate(LocalDateTime.now())
                .build();
    }

    public static VisitEntity createVisitEntityWithDatesAndStatus(LocalDateTime visitDateFrom,
                                                                  LocalDateTime visitDateTo,
                                                                  VisitStatus status) {
        return VisitEntity.builder()
                .visitStatus(status)
                .visitStartDate(visitDateFrom)
                .visitEndDate(visitDateTo)
                .build();
    }

    public static VisitEntity createVisitEntityWithDatesAndStatusAndPhysician(LocalDateTime visitDateFrom,
                                                                              LocalDateTime visitDateTo,
                                                                              VisitStatus status,
                                                                              String physicianId) {
        return VisitEntity.builder()
                .physicianId(physicianId)
                .visitStatus(status)
                .visitStartDate(visitDateFrom)
                .visitEndDate(visitDateTo)
                .build();
    }

    public static VisitEntity createVisitEntityWithPatientAndStatus(String patientId, VisitStatus visitStatus) {
        return VisitEntity.builder()
                .patientId(patientId)
                .visitStatus(visitStatus)
                .build();
    }

    public static VisitEntity createVisitEntityWithIdAndStatus(String visitId, VisitStatus visitStatus) {
        return VisitEntity.builder()
                .visitId(visitId)
                .visitStatus(visitStatus)
                .build();
    }

    public static VisitEntity createVisitEntityWithIdAndPatientAndStatus(String visitId, String patientId, VisitStatus visitStatus) {
        return VisitEntity.builder()
                .visitId(visitId)
                .patientId(patientId)
                .visitStatus(visitStatus)
                .build();
    }

    public static VisitEntity createVisitEntityWithPatient(String patientId) {
        return VisitEntity.builder()
                .patientId(patientId)
                .build();
    }

    public static VisitEntity createVisitEntityWithPhysician(String physicianId) {
        return VisitEntity.builder()
                .physicianId(physicianId)
                .build();
    }

    public static VisitEntity createVisitEntityWithPhysicianResponseAndFields(PhysicianResponseDTO physicianResponseDTO,
                                                                              String id,
                                                                              LocalDateTime visitDateFrom,
                                                                              LocalDateTime visitDateTo,
                                                                              VisitStatus status,
                                                                              String physicianId) {
        return VisitEntity.builder()
                .visitId(id)
                .physicianId(physicianId)
                .visitStartDate(visitDateFrom)
                .visitEndDate(visitDateTo)
                .visitStatus(status)
                .physicianRecommendations(List.of())
                .build();
    }

    public static VisitEntity createVisitEntityWithAllFields(String visitId,
                                                             String physicianId,
                                                             String specialization,
                                                             String patientId,
                                                             VisitStatus visitStatus,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate
    ) {
        return VisitEntity.builder()
                .visitId(visitId)
                .physicianId(physicianId)
                .physicianSpecialization(specialization)
                .patientId(patientId)
                .visitStatus(visitStatus)
                .visitStartDate(startDate)
                .visitEndDate(endDate)
                .physicianRecommendations(List.of())
                .build();
    }


    public static VisitUpdateDTO createVisitUpdateDtoWithAllFields(
            String physicianId,
            String patientId,
            String visitStatus,
            LocalDateTime visitDate) {
        return VisitUpdateDTO.builder()
                .physicianId(physicianId)
                .patientId(patientId)
                .visitStatus(visitStatus)
                .visitDate(visitDate)
                .build();
    }

    public static VisitUpdateDTO createVisitUpdateDtoWithStatus(
            String visitStatus) {
        return VisitUpdateDTO.builder()
                .visitStatus(visitStatus)
                .build();
    }

    public static VisitUpdateDTO createVisitUpdateDtoWithDate(LocalDateTime visitDateFrom) {
        return VisitUpdateDTO.builder()
                .patientId("paId")
                .physicianId("phId")
                .visitDate(visitDateFrom)
                .build();
    }

    public static VisitUpdateDTO createVisitUpdateDtoWithDateAndIds(LocalDateTime visitDateFrom,
                                                                    String physicianId,
                                                                    String patientId) {
        return VisitUpdateDTO.builder()
                .physicianId(physicianId)
                .patientId(patientId)
                .visitDate(visitDateFrom)
                .build();
    }

    public static VisitUpdateDTO createVisitUpdateDtoWithPatient(String patientId) {
        return VisitUpdateDTO.builder()
                .patientId(patientId)
                .build();
    }

    public static VisitUpdateDTO createVisitUpdateDtoWithPhysician(String physicianId) {
        return VisitUpdateDTO.builder()
                .physicianId(physicianId)
                .build();
    }

    public static PhysicianResponseDTO createPhysicianResponseWithSpecialization(String id, String spec) {
        return PhysicianResponseDTO.builder()
                .physicianId(id)
                .physiciansSpecialization(spec)
                .build();
    }

    public static PhysicianResponseDTO createPhysicianResponseDtoWithCustomFields(
            String id,
            String firstName,
            String lastName,
            String physicianSpecialization) {
        return PhysicianResponseDTO.builder()
                .physicianId(id)
                .firstName(firstName)
                .lastName(lastName)
                .physiciansSpecialization(physicianSpecialization)
                .build();
    }

    public static PhysicianResponseDTO createPhysicianResponseDtoWithSpecialization(String physicianSpecialization) {
        return PhysicianResponseDTO.builder()
                .physiciansSpecialization(physicianSpecialization)
                .build();
    }

    public static PatientResponseDTO createPatientResponseDTO(
            String patientId,
            String firstName,
            String lastName) {
        return PatientResponseDTO.builder()
                .patientId(patientId)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    public static PatientResponseDTO createPatientResponseDTOWithId(String patientId) {
        return PatientResponseDTO.builder()
                .patientId(patientId)
                .build();
    }

    public static VisitSimpleResponseDTO createVisitSimpleResponseDTO(
            PhysicianResponseDTO physicianResponseDTO, VisitEntity visitEntity) {
        return VisitSimpleResponseDTO.builder()
                .physicianResponseDTO(physicianResponseDTO)
                .visitStartDate(visitEntity.getVisitStartDate())
                .visitEndDate(visitEntity.getVisitEndDate())
                .visitStatus(visitEntity.getVisitStatus())
                .build();
    }

    public static VisitResponseDTO createVisitResponseDTO(
            PhysicianResponseDTO physicianResponseDTO, VisitEntity visitEntity) {
        return VisitResponseDTO.builder()
                .physicianResponseDTO(physicianResponseDTO)
                .visitStartDate(visitEntity.getVisitStartDate())
                .visitEndDate(visitEntity.getVisitEndDate())
                .visitStatus(visitEntity.getVisitStatus())
                .physicianRecommendations(List.of())
                .build();
    }

    public static PhysicianSpecializationDTO getPhysicianSpecializationDtoWithName(String name) {
        return PhysicianSpecializationDTO.builder().specializationName(name).build();
    }

}
