package com.alja.visit.service

import com.alja.common.enums.VisitStatus
import com.alja.visit.dto.VisitCommonFilterDTO
import com.alja.visit.fixtures.VisitFixtures
import com.alja.visit.model.repository.VisitRepository
import spock.lang.Specification

import java.time.LocalDateTime

import static com.alja.visit.VisitLogs.*

class VisitPatientServiceTest extends Specification {

    private VisitPatientService visitPatientService
    private VisitValidationService visitValidationService = Mock()
    private VisitQueryPredicateService visitQueryPredicateService = Mock()
    private VisitUpdateService visitUpdateService = Mock()
    private VisitResponseService visitResponseService = Mock()
    private VisitRepository visitRepository = Mock()
    private VisitSorter visitSorter = Mock()
    private LogService logService = Mock()

    def patientId = 'patientId'
    def visitId = "id"
    def visitStartDate = LocalDateTime.of(2020, 1, 1, 12, 0)
    def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
    def visitStatus = VisitStatus.AVAILABLE
    def physicianId = "physicianId"
    def firstName = 'Jan'
    def lastName = 'Dobry'
    def physicianSpecialization = 'radiologist'
    def physicianResponse
            = VisitFixtures.createPhysicianResponseDtoWithCustomFields(physicianId, firstName, lastName, physicianSpecialization)
    def visitEntity
            = VisitFixtures.createVisitEntityWithPhysicianResponseAndFields(physicianResponse, visitId,
            visitStartDate, visitEndDate, visitStatus, physicianId)
    def visitSimpleResponse = VisitFixtures.createVisitSimpleResponseDTO(physicianResponse, visitEntity)
    def visitResponse = VisitFixtures.createVisitResponseDTO(physicianResponse, visitEntity)
    def visitFilter
            = VisitFixtures.createVisitFilterWithStatusAndDates(visitStatus.name(), visitStartDate, visitEndDate)

    void setup() {
        visitPatientService = new VisitPatientService(
                visitValidationService,
                visitQueryPredicateService,
                visitUpdateService,
                visitResponseService,
                visitRepository,
                visitSorter,
                logService)
    }

    def 'should get all patients visits'() {
        when:
            visitPatientService.getAllPatientVisits(patientId)

        then:
            1 * logService.logOperation(GET_ALL_PATIENT_VISITS.logMessage, patientId)
            1 * visitValidationService.validateIfPatientPresent(patientId)
            1 * visitRepository.findAllByPatientId(patientId) >> [visitEntity]
            1 * visitSorter.sortVisitsDefault([visitEntity])
            1 * visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse
    }


    def 'should search available visits with filter'() {
        when:
            visitPatientService.searchAvailableVisitsWithFilter(visitFilter)

        then:
            1 * logService.logOperation(GET_VISITS_FILTERED.logMessage)
            1 * visitValidationService.validateDates(visitFilter)
            1 * visitValidationService.validatePhysicianIdAndSpecialization(visitFilter)
            1 * visitQueryPredicateService.getPredicatePatientVisits(visitFilter) >> _
            1 * visitSorter.getDefaultSort() >> _
            1 * visitRepository.findAll(_, _) >> [visitEntity]
            1 * visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse
    }


    def 'should get visit by id'() {
        when:
            visitPatientService.getVisitById(visitId)

        then:
            1 * logService.logOperation(GET_VISIT.logMessage, visitId)
            1 * visitValidationService.findVisitIfPresent(visitId) >> visitEntity
            1 * visitResponseService.getVisitResponse(visitEntity) >> visitResponse
    }

    def 'should appoint visit appointment'() {
        when:
            visitPatientService.makeVisitAppointment(patientId, visitId)

        then:
            1 * logService.logOperation(APPOINTING_VISIT.logMessage, patientId, visitId)
            1 * visitValidationService.validateIfPatientPresent(patientId)
            1 * visitValidationService.findVisitIfPresent(visitId) >> visitEntity
            1 * visitUpdateService.appointVisit(visitEntity, patientId)
            1 * visitRepository.save(visitEntity)
            1 * visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse
    }

    def 'should cancel visit appointment'() {
        when:
            visitPatientService.cancelVisitAppointment(patientId, visitId)

        then:
            1 * logService.logOperation(CANCELING_VISIT.logMessage, patientId, visitId)
            1 * visitValidationService.validateIfPatientPresent(patientId)
            1 * visitValidationService.findVisitIfPresent(visitId) >> visitEntity
            1 * visitUpdateService.cancelVisit(visitEntity)
            1 * visitRepository.save(visitEntity)
            1 * visitResponseService.getVisitSimpleResponse(visitEntity) >> visitSimpleResponse
    }

    def 'should invoke methods when invoke get visit simple response'() {
        when:
            visitPatientService.getVisitSimpleResponse(visitEntity)

        then:
            1 * visitResponseService.getVisitSimpleResponse(visitEntity)
    }

    def 'should invoke methods when invoke get visit response'() {
        when:
            visitPatientService.getVisitResponse(visitEntity)

        then:
            1 * visitResponseService.getVisitResponse(visitEntity)
    }

    def 'should invoke methods if arg visit filter is not null'() {
        given:
            def argVisitFilter = VisitCommonFilterDTO.builder().build()

        when:
            visitPatientService.getValidatedVisitFilter(argVisitFilter)

        then:
            1 * visitValidationService.validateDates(argVisitFilter)
            1 * visitValidationService.validatePhysicianIdAndSpecialization(argVisitFilter)
    }

    def 'should get empty visit filter if arg visit filter is null'() {
        given:
            def argVisitFilter = null

        when:
            def result = visitPatientService.getValidatedVisitFilter(argVisitFilter)

        then:
            result != null
            result.physicianId == null
            result.physicianSpecialization == null
            result.getVisitDateFrom() == null
            result.getVisitDateTo() == null
    }

}
