package com.alja.physician.model.repository;

import com.alja.physician.model.PhysicianEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhysicianRepository extends MongoRepository<PhysicianEntity, String> {
    List<String> findAllByContactDetailsPhoneNumber(String phoneNumber);
    List<String> findAllByContactDetailsEmail(String email);
}
