package com.alja.physician.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicianRepository extends MongoRepository<PhysicianEntity, String> {
}
