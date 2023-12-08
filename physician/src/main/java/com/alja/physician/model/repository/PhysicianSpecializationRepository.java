package com.alja.physician.model.repository;

import com.alja.physician.model.PhysicianSpecializationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicianSpecializationRepository extends MongoRepository<PhysicianSpecializationEntity, String> {

    PhysicianSpecializationEntity findBySpecializationName(String specializationName);

    boolean existsBySpecializationName(String specializationName);

    void deleteBySpecializationName(String specializationName);
}
