package com.alja.physician.model.repository;

import com.alja.physician.model.PhysicianSpecializationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PhysicianSpecializationRepository extends MongoRepository<PhysicianSpecializationEntity, String> {

    Optional<PhysicianSpecializationEntity> findBySpecializationName(String specializationName);
    boolean existsBySpecializationName(String specializationName);
    void deleteBySpecializationName(String specializationName);
}
