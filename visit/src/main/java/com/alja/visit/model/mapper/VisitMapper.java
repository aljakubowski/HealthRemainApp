package com.alja.visit.model.mapper;

import com.alja.visit.dto.VisitNewDTO;
import com.alja.visit.model.VisitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class,
        java.util.UUID.class, com.alja.common.enums.VisitStatus.class})
public interface VisitMapper {

    @Mapping(target = "visitId", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "physicianId", source = "visitNewDTO.physicianId")
    @Mapping(target = "physicianSpecialization", source = "physicianSpecialization")
    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "visitStatus", expression = "java(VisitStatus.AVAILABLE)")
    @Mapping(target = "visitStartDate", source = "visitNewDTO.visitDate")
    @Mapping(target = "visitEndDate", source = "visitEndDate")
    @Mapping(target = "physicianRecommendations", ignore = true)
    VisitEntity toVisitEntity(VisitNewDTO visitNewDTO, String physicianSpecialization, LocalDateTime visitEndDate);
}
