package com.alja.visit.service;

import com.alja.common.enums.VisitStatus;
import com.alja.visit.dto.VisitFilterDTO;
import com.alja.visit.model.QVisitEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class VisitQueryPredicateService {

    public Predicate getPredicateVisits(VisitFilterDTO visitFilter) {

        QVisitEntity qVisitEntity = new QVisitEntity("visit");
        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(qVisitEntity.visitStatus.eq(getStatus(visitFilter.getVisitStatus())));

        if (validFieldValue(visitFilter.getPhysicianId())) {
            predicate.and(qVisitEntity.physicianId.eq(visitFilter.getPhysicianId()));
        }

        if (validFieldValue(visitFilter.getPhysicianSpecialization())) {
            predicate.and(qVisitEntity.physicianSpecialization.eq(visitFilter.getPhysicianSpecialization()));
        }

        if (validFieldValue(visitFilter.getPatientId())) {
            predicate.and(qVisitEntity.patientId.eq(visitFilter.getPatientId()));
        }
        if (validDateFieldValue(visitFilter.getVisitDateFrom())) {
            predicate.and(qVisitEntity.visitStartDate.goe(visitFilter.getVisitDateFrom()));
        }

        if (validDateFieldValue(visitFilter.getVisitDateTo())) {
            predicate.and(qVisitEntity.visitEndDate.loe(visitFilter.getVisitDateTo()));
        }

        return predicate;
    }

    private boolean validFieldValue(String fieldValue) {
        return StringUtils.isNotBlank(fieldValue);
    }

    private boolean validDateFieldValue(LocalDateTime localDateTime) {
        return localDateTime != null;
    }

    private VisitStatus getStatus(String status) {
        return VisitStatus.valueOf(status.toUpperCase());
    }

}
