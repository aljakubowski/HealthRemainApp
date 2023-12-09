package com.alja.physician.model.mapper;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.model.PhysicianSpecializationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhysicianSpecializationMapper {

    PhysicianSpecializationDTO specializationEntityToDto(PhysicianSpecializationEntity entity);
}