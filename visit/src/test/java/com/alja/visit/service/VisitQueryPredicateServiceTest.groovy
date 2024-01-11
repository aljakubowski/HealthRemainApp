package com.alja.visit.service

import com.alja.common.enums.VisitStatus
import com.alja.visit.fixtures.VisitFixtures
import spock.lang.Specification

import java.time.LocalDateTime

class VisitQueryPredicateServiceTest extends Specification {

    private VisitQueryPredicateService visitQueryPredicateService

    void setup() {
        visitQueryPredicateService = new VisitQueryPredicateService()
    }

    def 'should create query predicate with filter dto with all fields'() {
        given:
            def physicianId = 'physicianId'
            def physicianSpecialization = 'Radiologist'
            def patientId = 'patientId'
            def visitStatus = 'AVAILABLE'
            def visitDateFromString = '2024-08-01T10:00'
            def visitDateFrom = LocalDateTime.parse(visitDateFromString)
            def visitDateToString = '2024-01-25T18:00'
            def visitDateTo = LocalDateTime.parse(visitDateToString)

            def visitFilterDto = VisitFixtures.createVisitFilterWithAllFields(physicianId,
                    physicianSpecialization, patientId, visitStatus, visitDateFrom, visitDateTo)

        when:
            def result = visitQueryPredicateService.getPredicateVisits(visitFilterDto)
        then:
            result.toString() ==
                    "visit.visitStatus = " + visitStatus +
                    " && visit.physicianId = " + physicianId +
                    " && visit.physicianSpecialization = " + physicianSpecialization +
                    " && visit.patientId = " + patientId +
                    " && visit.visitStartDate >= " + visitDateFromString +
                    " && visit.visitEndDate <= " + visitDateToString

    }

    def 'should create query predicate with filter dto with some fields'() {
        given:
            def physicianSpecialization = 'Radiologist'
            def visitStatus = 'AVAILABLE'
            def visitDateFromString = '2024-08-01T10:00'
            def visitDateFrom = LocalDateTime.parse(visitDateFromString)

            def visitFilterDto
                    = VisitFixtures.createVisitFilterWithSpecializationAndStatusAndDateFrom(physicianSpecialization,
                    visitStatus, visitDateFrom)

        when:
            def result = visitQueryPredicateService.getPredicateVisits(visitFilterDto)
        then:
            result.toString() ==
                    "visit.visitStatus = " + visitStatus +
                    " && visit.physicianSpecialization = " + physicianSpecialization +
                    " && visit.visitStartDate >= " + visitDateFromString

    }

    def 'should create query predicate with common filter dto'() {
        given:
            def physicianSpecialization = 'Radiologist'
            def visitStatus = 'AVAILABLE'
            def visitDateFromString = '2024-08-01T10:00'
            def visitDateFrom = LocalDateTime.parse(visitDateFromString)
            def visitDateToString = '2024-10-01T10:00'
            def visitDateTo = LocalDateTime.parse(visitDateToString)

            def visitFilterDto = VisitFixtures.createVisitCommonFilter(physicianSpecialization,
                    visitDateFrom, visitDateTo)

        when:
            def result = visitQueryPredicateService.getPredicatePatientVisits(visitFilterDto)
        then:
            result.toString() ==
                    "visit.visitStatus = " + visitStatus +
                    " && visit.physicianSpecialization = " + physicianSpecialization +
                    " && visit.visitStartDate >= " + visitDateFromString +
                    " && visit.visitEndDate <= " + visitDateToString
    }

    def 'should return appropriate boolean when validating field value'(String fieldValue) {
        expect:
            visitQueryPredicateService.validFieldValue(fieldValue) == result

        where:
            fieldValue   || result
            'fieldValue' || true
            ''           || false
            ' '          || false
            null         || false
    }

    def 'should return appropriate boolean when validating date field value'(LocalDateTime dateTimeValue) {
        expect:
            visitQueryPredicateService.validDateFieldValue(dateTimeValue) == result

        where:
            dateTimeValue       || result
            LocalDateTime.now() || true
            null                || false
    }

    def 'should return appropriate VisitStatus from string value'(String stringStatus) {
        expect:
            visitQueryPredicateService.getStatus(stringStatus) == result

        where:
            stringStatus || result
            'available'  || VisitStatus.AVAILABLE
            'reSERVED'   || VisitStatus.RESERVED
            'COMPLETED'  || VisitStatus.COMPLETED
            'unrealizeD' || VisitStatus.UNREALIZED
    }

}
