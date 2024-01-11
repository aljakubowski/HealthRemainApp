package com.alja.visit.service

import com.alja.common.enums.VisitStatus
import com.alja.visit.fixtures.VisitFixtures
import spock.lang.Specification

import java.time.LocalDateTime

class VisitResponseServiceTest extends Specification {

    private VisitResponseService visitResponseService
    private ClientsService clientsService = Mock()

    void setup(){
        visitResponseService = new VisitResponseService(clientsService)
    }

    def 'should get visit simple response dto'() {
        given:
            def physicianId = "physicianId"
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'

            def visitId = "id"
            def visitStartDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def visitStatus = VisitStatus.AVAILABLE

            def physicianResponse
                    = VisitFixtures.createPhysicianResponseDtoWithCustomFields(physicianId, firstName, lastName, physicianSpecialization)

            def visitEntity
                    = VisitFixtures.createVisitEntityWithPhysicianResponseAndFields(physicianResponse, visitId,
                    visitStartDate, visitEndDate, visitStatus, physicianId)
            clientsService.getPhysicianResponseDTO(physicianId) >> physicianResponse

        when:
            def result = visitResponseService.getVisitSimpleResponse(visitEntity)

        then:
            result != null
            result.visitId == visitId
            result.physicianResponseDTO.physicianId == physicianId
            result.physicianResponseDTO.firstName == firstName
            result.physicianResponseDTO.lastName == lastName
            result.physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.visitStartDate == visitStartDate
            result.visitEndDate == visitEndDate
            result.visitStatus == visitStatus
    }

    def 'should get visit response dto'() {
        given:
            def physicianId = "physicianId"
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'

            def visitId = "id"
            def visitStartDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def visitStatus = VisitStatus.AVAILABLE

            def physicianResponse
                    = VisitFixtures.createPhysicianResponseDtoWithCustomFields(physicianId, firstName, lastName, physicianSpecialization)

            def visitEntity
                    = VisitFixtures.createVisitEntityWithPhysicianResponseAndFields(physicianResponse, visitId,
                    visitStartDate, visitEndDate, visitStatus, physicianId)
            clientsService.getPhysicianResponseDTO(physicianId) >> physicianResponse

        when:
            def result = visitResponseService.getVisitResponse(visitEntity)

        then:
            result != null
            result.visitId == visitId
            result.physicianResponseDTO.physicianId == physicianId
            result.physicianResponseDTO.firstName == firstName
            result.physicianResponseDTO.lastName == lastName
            result.physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.visitStartDate == visitStartDate
            result.visitEndDate == visitEndDate
            result.visitStatus == visitStatus
            result.physicianRecommendations.size() == 0
    }

    def 'should get physician response'() {
        given:
            def physicianId = "physicianId"
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def physicianResponse
                    = VisitFixtures.createPhysicianResponseDtoWithCustomFields(physicianId, firstName, lastName, physicianSpecialization)

            clientsService.getPhysicianResponseDTO(physicianId) >> physicianResponse

        when:
            def result = visitResponseService.getPhysicianResponse(physicianId)

        then:
            result != null
            result.physicianId == physicianId
            result.firstName == firstName
            result.lastName == lastName
            result.physiciansSpecialization == physicianSpecialization
    }

    def 'should get patient response'() {
        given:
            def patientId = "patientId"
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def patientResponse = VisitFixtures.createPatientResponseDTO(patientId, firstName, lastName)

            clientsService.getPatientResponseDTO(patientId) >> patientResponse

        when:
            def result = visitResponseService.getPatientResponseIfRegisteredVisit(patientId)

        then:
            result != null
            result.patientId == patientId
            result.firstName == firstName
            result.lastName == lastName
    }

}
