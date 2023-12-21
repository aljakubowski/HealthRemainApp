package com.alja.patient.model.repository;

import com.alja.patient.model.PatientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<PatientEntity, String> {

    List<String> findAllBySocialSecurityNumber(String socialSecurityNumber);
    List<String> findAllByContactDetailsPhoneNumber(String phoneNumber);
    List<String> findAllByContactDetailsEmail(String email);
    Optional<PatientEntity> findPatientEntityByPatientId(String id);
}
