package com.alja.physician.model.repository;

import com.alja.physician.model.PhysicianEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicianRepository extends MongoRepository<PhysicianEntity, String> {
}
