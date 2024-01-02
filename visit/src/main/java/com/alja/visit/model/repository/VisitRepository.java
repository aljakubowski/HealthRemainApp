package com.alja.visit.model.repository;

import com.alja.visit.model.VisitEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VisitRepository extends MongoRepository<VisitEntity, String> {
    List<VisitEntity> getVisitEntitiesByPhysicianId(String physicianId);
}
