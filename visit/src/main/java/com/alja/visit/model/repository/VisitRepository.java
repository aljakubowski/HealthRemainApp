package com.alja.visit.model.repository;

import com.alja.visit.model.VisitEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<VisitEntity, String> {
}
