package com.alja.visit.fixtures;

import com.alja.common.enums.VisitStatus;
import com.alja.patient.dto.PatientRegisterDTO;
import com.alja.patient.dto.PatientResponseDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.visit.dto.VisitFilterDTO;
import com.alja.visit.dto.VisitNewDTO;
import com.alja.visit.dto.VisitUpdateDTO;
import com.alja.visit.model.VisitEntity;

import java.time.LocalDateTime;

public class VisitFixtures {

    public static VisitNewDTO createVisitNewDTO(String physicianId, LocalDateTime visitDate) {
        return VisitNewDTO.builder()
                .physicianId(physicianId)
                .visitDate(visitDate)
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
}
