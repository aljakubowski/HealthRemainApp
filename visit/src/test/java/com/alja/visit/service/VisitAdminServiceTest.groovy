package com.alja.visit.service

import com.alja.common.enums.VisitStatus
import com.alja.visit.fixtures.VisitFixtures
import com.alja.visit.model.mapper.VisitMapper
import com.alja.visit.model.repository.VisitRepository
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Sort
import spock.lang.Specification

import java.time.LocalDateTime

class VisitAdminServiceTest extends Specification {

    private VisitAdminService visitAdminService
    private VisitValidationService visitValidationService = Mock()
    private VisitQueryPredicateService visitQueryPredicateService = Mock()
    private VisitUpdateService visitUpdateService = Mock()
    private VisitResponseService visitResponseService = Mock()
    private VisitRepository visitRepository = Mock()
    private VisitMapper visitMapper = Mock()
    private VisitSorter visitSorter = Mock()
    private LogService logService = Mock()

    def setup() {
        visitAdminService = new VisitAdminService(
                visitValidationService,
                visitQueryPredicateService,
                visitUpdateService,
                visitResponseService,
                visitRepository,
                visitMapper,
                visitSorter,
                logService
        )
    }


    def 'should add a new visit'() {
        given:
            def visitDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def visitNewDTO = VisitFixtures.createVisitNewDTO(physicianId, visitDate)
            def visitStatus = VisitStatus.AVAILABLE

            def physicianResponse = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianId, firstName, lastName, physicianSpecialization)

            def visitEntity = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDate, visitEndDate, visitStatus, physicianId)

            def visitSimpleResponse
                    = VisitFixtures.createVisitSimpleResponseDTO(physicianResponse, visitEntity)

            visitAdminService.getPhysicianSpecialization(physicianId) >> physicianSpecialization
            visitMapper.toVisitEntity(visitNewDTO, physicianSpecialization, visitEndDate) >> visitEntity
            visitValidationService.getVisitEndDate(visitNewDTO.visitDate) >> visitEndDate
            visitResponseService.getPhysicianResponse(physicianId) >> physicianResponse
            visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse

        when:
            def result = visitAdminService.addNewVisit(visitNewDTO)

        then:
            result != null
            result.physicianResponseDTO.physicianId == physicianId
            result.physicianResponseDTO.firstName == firstName
            result.physicianResponseDTO.lastName == lastName
            result.physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.visitStartDate == visitDate
            result.visitEndDate == visitEndDate
            result.visitStatus == visitStatus
    }


    def 'should get visit by id'() {
        given:
            def visitId = "id"
            def visitDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def visitNewDTO = VisitFixtures.createVisitNewDTO(physicianId, visitDate)
            def visitStatus = VisitStatus.AVAILABLE

            def physicianResponse = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianId, firstName, lastName, physicianSpecialization)

            def visitEntity = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDate, visitEndDate, visitStatus, physicianId)

            def visitResponse
                    = VisitFixtures.createVisitResponseDTO(physicianResponse, visitEntity)

            visitMapper.toVisitEntity(visitNewDTO, physicianSpecialization, visitEndDate) >> visitEntity
            visitValidationService.getVisitEndDate(visitNewDTO.visitDate) >> visitEndDate
            visitValidationService.findVisitIfPresent(visitId) >> visitEntity
            visitResponseService.getVisitResponse(visitEntity) >> visitResponse

        when:
            def result = visitAdminService.getVisitById(visitId)

        then:
            result != null
            result.physicianResponseDTO.physicianId == physicianId
            result.physicianResponseDTO.firstName == firstName
            result.physicianResponseDTO.lastName == lastName
            result.physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.visitStartDate == visitDate
            result.visitEndDate == visitEndDate
            result.visitStatus == visitStatus
            result.physicianRecommendations.size() == 0
    }

    def 'should get visits with filter'() {
        given:
            def visitDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateFilter = LocalDateTime.of(2020, 1, 1, 9, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def visitEndDateFilter = LocalDateTime.of(2020, 2, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = ' '
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def visitNewDTO = VisitFixtures.createVisitNewDTO(physicianId, visitDate)
            def visitStatus = VisitStatus.AVAILABLE

            def physicianResponse = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianId, firstName, lastName, physicianSpecialization)

            def visitEntity = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDate, visitEndDate, visitStatus, physicianId)

            def visitFilter = VisitFixtures.createVisitFilterWithAllFields(
                    physicianId, physicianSpecialization, patientId, visitStatus.name(), visitDateFilter, visitEndDateFilter)

            def visitSimpleResponse
                    = VisitFixtures.createVisitSimpleResponseDTO(physicianResponse, visitEntity)

            visitMapper.toVisitEntity(visitNewDTO, physicianSpecialization, visitEndDate) >> visitEntity
            visitValidationService.getVisitEndDate(visitNewDTO.visitDate) >> visitEndDate
            visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse
            visitQueryPredicateService.getPredicateVisits(visitFilter) >> _
            visitSorter.getDefaultSort() >> _
            visitRepository.findAll(_ as Predicate, _ as Sort) >> [visitEntity]


        when:
            def result = visitAdminService.getVisitsWithFilter(visitFilter)

        then:
            result != null
            result.get(0).physicianResponseDTO.physicianId == physicianId
            result.get(0).physicianResponseDTO.firstName == firstName
            result.get(0).physicianResponseDTO.lastName == lastName
            result.get(0).physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.get(0).visitStartDate == visitDate
            result.get(0).visitEndDate == visitEndDate
            result.get(0).visitStatus == visitStatus
    }

    def 'should update visit'() {
        given:
            def visitId = "id"
            def visitDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def visitStatus = VisitStatus.AVAILABLE

            def visitEntity = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDate, visitEndDate, visitStatus, physicianId)

            def visitDateUpdated = LocalDateTime.of(2021, 1, 1, 12, 0)
            def visitEndDateUpdated = LocalDateTime.of(2021, 1, 1, 12, 30)
            def physicianIdUpdated = 'physicianIdUpdated'
            def patientIdUpdated = 'patientIdUpdated'
            def firstNameUpdated = 'Robert'
            def lastNameUpdated = 'Lepszy'
            def physicianSpecializationUpdated = 'cardiologist'
            def visitStatusUpdated = VisitStatus.RESERVED

            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithAllFields(physicianIdUpdated, patientIdUpdated,
                    visitStatusUpdated.name(), visitDateUpdated)

            def visitEntityUpdated = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDateUpdated, visitEndDateUpdated, visitStatusUpdated, physicianIdUpdated)

            def physicianResponse = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianIdUpdated, firstNameUpdated, lastNameUpdated, physicianSpecializationUpdated)

            def visitSimpleResponse
                    = VisitFixtures.createVisitSimpleResponseDTO(physicianResponse, visitEntityUpdated)

            visitValidationService.findVisitIfPresent(visitId) >> visitEntity
            visitUpdateService.updateVisit(visitEntity, visitUpdateDTO) >> visitEntityUpdated
            visitResponseService.getVisitSimpleResponse(visitEntityUpdated) >> visitSimpleResponse

        when:
            def result = visitAdminService.updateVisit(visitId, visitUpdateDTO)

        then:
            result != null
            result.physicianResponseDTO.physicianId == physicianIdUpdated
            result.physicianResponseDTO.firstName == firstNameUpdated
            result.physicianResponseDTO.lastName == lastNameUpdated
            result.physicianResponseDTO.physiciansSpecialization == physicianSpecializationUpdated
            result.visitStartDate == visitDateUpdated
            result.visitEndDate == visitEndDateUpdated
            result.visitStatus == visitStatusUpdated
    }

    def 'should delete visit by id'() {
        given:
            def visitId = "id"
            def visitDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def visitStatus = VisitStatus.AVAILABLE

            def visitEntity = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDate, visitEndDate, visitStatus, physicianId)

            def physicianResponse = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianId, firstName, lastName, physicianSpecialization)

            def visitSimpleResponse
                    = VisitFixtures.createVisitSimpleResponseDTO(physicianResponse, visitEntity)

            visitValidationService.findVisitIfPresent(visitId) >> visitEntity
            visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse

        when:
            def result = visitAdminService.deleteVisitById(visitId)

        then:
            result != null
            result.physicianResponseDTO.physicianId == physicianId
            result.physicianResponseDTO.firstName == firstName
            result.physicianResponseDTO.lastName == lastName
            result.physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.visitStartDate == visitDate
            result.visitEndDate == visitEndDate
            result.visitStatus == visitStatus
    }

    def 'should get all visits'() {
        given:
            def visitDate = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def visitStatus = VisitStatus.AVAILABLE

            def physicianResponse = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianId, firstName, lastName, physicianSpecialization)

            def visitEntity = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(
                    visitDate, visitEndDate, visitStatus, physicianId)

            def visitSimpleResponse
                    = VisitFixtures.createVisitSimpleResponseDTO(physicianResponse, visitEntity)

            visitSorter.getDefaultSort() >> _
            visitRepository.findAll(_) >> [visitEntity]
            visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse

        when:
            def result = visitAdminService.getAllVisit()

        then:
            result != null
            result.size() == 1
            result.get(0).physicianResponseDTO.physicianId == physicianId
            result.get(0).physicianResponseDTO.firstName == firstName
            result.get(0).physicianResponseDTO.lastName == lastName
            result.get(0).physicianResponseDTO.physiciansSpecialization == physicianSpecialization
            result.get(0).visitStartDate == visitDate
            result.get(0).visitEndDate == visitEndDate
            result.get(0).visitStatus == visitStatus
    }

    def 'should get physician specialization string'() {
        given:
            def physicianId = "physicianId"
            def physicianSpecialization = 'radiologist'

            def physicianResponse
                    = VisitFixtures.createPhysicianResponseDtoWithSpecialization(physicianSpecialization)

            visitResponseService.getPhysicianResponse(physicianId) >> physicianResponse

        when:
            def result = visitAdminService.getPhysicianSpecialization(physicianId)

        then:
            result == physicianSpecialization
    }

}
