package com.alja.visit.model.mapper

import com.alja.common.enums.VisitStatus
import com.alja.visit.fixtures.VisitFixtures
import spock.lang.Specification

import java.time.LocalDateTime

class VisitMapperTest extends Specification {
    VisitMapper visitMapper

    def setup() {
        visitMapper = new VisitMapperImpl() {}
    }

    def "should map Visit dto to entity"() {
        given:
            def physicianId = '123'
            def visitDate = LocalDateTime.of(2023, 10, 18, 13, 0)
            def visitNewDto = VisitFixtures.createVisitNewDTO(physicianId, visitDate)
            def physicianSpecialization = "radiologist"
            def visitEndDate = LocalDateTime.of(2023, 10, 18, 13, 30)

        when:
            def result = visitMapper.toVisitEntity(visitNewDto, physicianSpecialization, visitEndDate)

        then:
            result.physicianId == physicianId
            result.physicianSpecialization == physicianSpecialization
            result.patientId == null
            result.visitStatus == VisitStatus.AVAILABLE
            result.visitStartDate == visitDate
            result.visitEndDate == visitEndDate
            result.physicianRecommendations == null
    }
}
