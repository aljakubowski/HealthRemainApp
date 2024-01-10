package com.alja.visit.model.repository;

import com.alja.visit.model.VisitEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends MongoRepository<VisitEntity, String>
        , QuerydslPredicateExecutor<VisitEntity>
{

    Optional<VisitEntity> findVisitEntityByVisitId(String id);

    List<VisitEntity> findVisitEntitiesByPhysicianId(String id);
    List<VisitEntity> findVisitEntitiesByPatientId(String id);

    //

    List<VisitEntity> findAllByPatientId(String id);


}
