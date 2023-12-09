package com.alja.physician.fixtures;

import com.alja.physician.dto.PhysicianSpecializationDTO;
import com.alja.physician.model.PhysicianSpecializationEntity;

public class PhysicianFixtures {

    public static PhysicianSpecializationEntity createSpecialization(String specializationName){
        return PhysicianSpecializationEntity.builder()
                .specializationName(specializationName)
                .build();
    }

    public static PhysicianSpecializationDTO createSpecializationDto(String specializationName){
        return PhysicianSpecializationDTO.builder()
                .specializationName(specializationName)
                .build();
    }
}
