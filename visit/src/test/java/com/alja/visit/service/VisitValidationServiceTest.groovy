package com.alja.visit.service

import com.alja.common.enums.VisitStatus
import com.alja.errors.VisitError
import com.alja.exception.VisitException
import com.alja.visit.fixtures.VisitFixtures
import com.alja.visit.model.VisitEntity
import com.alja.visit.model.repository.VisitRepository
import spock.lang.Specification

import java.time.LocalDateTime
import java.util.stream.Collectors

class VisitValidationServiceTest extends Specification {

    private VisitValidationService visitValidationService
    private VisitRepository visitRepository = Mock(VisitRepository)
    private ClientsService clientsService = Mock()

    def setup() {
        visitValidationService = new VisitValidationService(visitRepository, clientsService)
    }

    def 'should return visit end date'() {
        given:
            def visitStartDate = LocalDateTime.of(2023, 1, 1, 10, 0)
            def expectedVisitEndDate = LocalDateTime.of(2023, 1, 1, 10, 30)

        when:
            def actual = visitValidationService.getVisitEndDate(visitStartDate)

        then:
            actual == expectedVisitEndDate
    }

    def 'should throw exception when visit does not exist'() {
        given:
            def id = "id"
            visitRepository.findVisitEntityByVisitId(id) >> Optional.empty()

        when:
            def actual = visitValidationService.findVisitIfPresent(id)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_NOT_FOUND_ERROR.getMessage()
    }

    def 'should not throw exception when visit exist'() {
        given:
            def id = "id"
            visitRepository.findVisitEntityByVisitId(id) >> Optional.of(VisitEntity.builder().build())

        when:
            visitValidationService.findVisitIfPresent(id)

        then:
            noExceptionThrown()
    }


    def 'should not throw exception when visit status is valid'() {
        given:
            def validVisitStatus = "AVAILABLE"

        when:
            visitValidationService.validateStatus(validVisitStatus)

        then:
            noExceptionThrown()
    }

    def 'should not throw exception when visit status is lower case'() {
        given:
            def validVisitStatus = "completed"

        when:
            visitValidationService.validateStatus(validVisitStatus)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when visit status is invalid'() {
        given:
            def invalidStatus = 'invalid'
            def availableStatuses = Arrays.stream(VisitStatus.values())
                    .map(Enum::name)
                    .collect(Collectors.toList())
            visitValidationService.getVisitStatusList() >> availableStatuses

        when:
            def actual = visitValidationService.validateStatus(invalidStatus)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_INVALID_STATUS_ERROR.getMessage() + availableStatuses.toString()
    }

    def 'should return if both dates are null'() {
        given:
            def visitDateFrom = null
            def visitDateTo = null
            def visitStatus = VisitStatus.AVAILABLE.name()
            def visitFilter
                    = VisitFixtures.createVisitFilterWithStatusAndDatesAndId(visitStatus, visitDateFrom, visitDateTo)

        when:
            visitValidationService.validateDates(visitFilter)

        then:
            0 * visitValidationService.validateDateRange(visitDateFrom, visitDateTo)
    }

    def 'should throw exception when dateFrom is after dateBefore'() {
        given:
            def dateBefore = LocalDateTime.of(2020, 1, 1, 12, 0)
            def dateAfter = LocalDateTime.of(2020, 1, 1, 11, 0)

        when:
            def actual = visitValidationService.validateDateRange(dateBefore, dateAfter)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_DATE_INVALID_RANGE_ERROR.getMessage()
    }

    def 'should not throw exception when dateFrom is before dateBefore'() {
        given:
            def dateBefore = LocalDateTime.of(2020, 1, 1, 12, 0)
            def dateAfter = LocalDateTime.of(2020, 1, 1, 13, 0)

        when:
            visitValidationService.validateDateRange(dateBefore, dateAfter)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when searching with physician id and physician specialization'() {
        given:
            def physicianId = 'physicianId'
            def physicianSpecialization = 'Radiologist'
            def visitFilter
                    = VisitFixtures.createVisitFilterWithPhysicianIdAndSpecialization(physicianId, physicianSpecialization)

        when:
            def actual = visitValidationService.validatePhysicianIdAndSpecialization(visitFilter)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_INVALID_SEARCH_ERROR.getMessage()
    }

    def 'should not throw exception when searching with physician id or physician specialization'() {
        given:
            def physicianId = null
            def physicianSpecialization = 'Radiologist'
            def visitFilter
                    = VisitFixtures.createVisitFilterWithPhysicianIdAndSpecialization(physicianId, physicianSpecialization)
            clientsService.getAvailableSpecializations() >> [physicianSpecialization]

        when:
            visitValidationService.validatePhysicianIdAndSpecialization(visitFilter)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when not found physician specialization'() {
        given:
            def requestedPhysicianSpecialization = 'Radiology'
            def physicianSpecialization = 'Radiologist'
            clientsService.getAvailableSpecializations() >> [physicianSpecialization]

        when:
            def actual = visitValidationService.validateAvailablePhysicianSpecializations(requestedPhysicianSpecialization)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_INVALID_PHYSICIAN_SPECIALIZATION_ERROR.getMessage() + [physicianSpecialization]
    }

    def 'should not throw exception when not found physician specialization'() {
        given:
            def requestedPhysicianSpecialization = 'Radiologist'
            def physicianSpecialization = 'Radiologist'
            clientsService.getAvailableSpecializations() >> [physicianSpecialization]

        when:
            visitValidationService.validateAvailablePhysicianSpecializations(requestedPhysicianSpecialization)

        then:
            noExceptionThrown()
    }

    def 'should not throw exception when searching with existing patient id'() {
        given:
            def patientId = 'patientId'
            def patientResponse = VisitFixtures.createPatientResponseDTOWithId(patientId)
            clientsService.getPatientResponseDTO(patientId) >> patientResponse

        when:
            visitValidationService.validateIfPatientPresent(patientId)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when visit is not available'() {
        given:
            def visitStatus = VisitStatus.RESERVED

        when:
            def actual = visitValidationService.validateIfReserved(visitStatus)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_RESERVED_ERROR.getMessage()
    }

    def 'should not throw exception when visit is available'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE

        when:
            visitValidationService.validateIfReserved(visitStatus)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when visit not belong to patient'() {
        given:
            def visitId = 'visitId'
            def visitPatientId = 'visitPatientId'

        when:
            def actual = visitValidationService.validateIfPatientsVisit(visitId, visitPatientId)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_INVALID_ID_ERROR.getMessage()
    }

    def 'should not throw exception when visit belong to patient'() {
        given:
            def visitId = 'visitId'
            def visitPatientId = 'visitId'

        when:
            visitValidationService.validateIfPatientsVisit(visitId, visitPatientId)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when visit is reserved'() {
        given:
            def visitPatientId = 'visitPatientId'
            def visitStatus = VisitStatus.RESERVED

        when:
            def actual = visitValidationService.validateIfVisitAvailable(visitPatientId, visitStatus)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_NOT_AVAILABLE_ERROR.getMessage()
    }

    def 'should not throw exception when visit is available or has patient'() {
        given:
            def visitPatientId = ''
            def visitStatus = VisitStatus.AVAILABLE

        when:
            visitValidationService.validateIfVisitAvailable(visitPatientId, visitStatus)

        then:
            noExceptionThrown()
    }

    def 'should return true when checking statuses Available and Reserved'(VisitStatus visitStatus) {
        expect:
            visitValidationService.isAvailableOrReserved(visitStatus) == result

        where:
            visitStatus            || result
            VisitStatus.AVAILABLE  || true
            VisitStatus.RESERVED   || true
            VisitStatus.COMPLETED  || false
            VisitStatus.UNREALIZED || false
    }

    def 'should return true when checking statuses Completed and Unrealized'(VisitStatus visitStatus) {
        expect:
            visitValidationService.isCompletedOrUnrealized(visitStatus) == result

        where:
            visitStatus            || result
            VisitStatus.AVAILABLE  || false
            VisitStatus.RESERVED   || false
            VisitStatus.COMPLETED  || true
            VisitStatus.UNREALIZED || true
    }

    def 'should throw exception when date is not future'() {
        given:
            def testedDate = LocalDateTime.of(2000, 1, 1, 11, 30)

        when:
            def actual = visitValidationService.checkIfFutureDate(testedDate)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_PAST_DATE_INVALID_ERROR.getMessage()
    }

    def 'should not throw exception when date is future'() {
        given:
            def testedDate = LocalDateTime.of(2100, 1, 1, 11, 30)

        when:
            visitValidationService.checkIfFutureDate(testedDate)

        then:
            noExceptionThrown()
    }

    def 'should throw exception when date is not past'() {
        given:
            def testedDate = LocalDateTime.of(2100, 1, 1, 11, 30)

        when:
            def actual = visitValidationService.checkIfPastDate(testedDate)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_FUTURE_DATE_INVALID_ERROR.getMessage()
    }

    def 'should not throw exception when date is past'() {
        given:
            def testedDate = LocalDateTime.of(2000, 1, 1, 11, 30)

        when:
            visitValidationService.checkIfPastDate(testedDate)

        then:
            noExceptionThrown()
    }

    def 'should throw exception if visit coincides'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 11, 30)
            def visitDateStart = LocalDateTime.of(2023, 1, 1, 12, 0)
            def visitDateEnd = LocalDateTime.of(2023, 1, 1, 12, 30)

        when:
            def actual = visitValidationService.checkIfVisitCoincide(requestedVisitDate, visitDateStart, visitDateEnd)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_COINCIDE_ERROR.getMessage()

    }

    def 'should not throw exception if visit does not coincide'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 9, 0)
            def visitDateStart = LocalDateTime.of(2023, 1, 1, 12, 0)
            def visitDateEnd = LocalDateTime.of(2023, 1, 1, 12, 30)

        when:
            visitValidationService.checkIfVisitCoincide(requestedVisitDate, visitDateStart, visitDateEnd)

        then:
            noExceptionThrown()
    }

    def 'should throw exception if patient id is empty'() {
        given:
            def patientId = ''

        when:
            def actual = visitValidationService.validateIfEmptyId(patientId)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_INVALID_PATIENT_ERROR.getMessage()

    }

    def 'should not throw exception if patient id is not empty'() {
        given:
            def patientId = 'patientId'
        when:
            visitValidationService.validateIfEmptyId(patientId)

        then:
            noExceptionThrown()
    }











    def 'should return true if requestedVisitDate is equal tested date in dateIsAfterOrEqual'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 10, 0)
            def testedDate = requestedVisitDate

        when:
            def actual = visitValidationService.dateIsAfterOrEqual(requestedVisitDate, testedDate)

        then:
            actual
    }

    def 'should return true if requestedVisitDate is after or equal tested date'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 11, 0)
            def testedDate = LocalDateTime.of(2023, 1, 1, 10, 0)

        when:
            def actual = visitValidationService.dateIsAfterOrEqual(requestedVisitDate, testedDate)

        then:
            actual
    }

    def 'should return false if requestedVisitDate is before tested date'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 9, 50)
            def testedDate = LocalDateTime.of(2023, 1, 1, 10, 0)

        when:
            def actual = visitValidationService.dateIsAfterOrEqual(requestedVisitDate, testedDate)

        then:
            !actual
    }

    def 'should return true if requestedVisitDate is equal tested date in dateIsBeforeOrEqual'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 10, 0)
            def testedDate = requestedVisitDate

        when:
            def actual = visitValidationService.dateIsBeforeOrEqual(requestedVisitDate, testedDate)

        then:
            actual
    }

    def 'should return true if requestedVisitDate is before tested date'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 9, 0)
            def testedDate = LocalDateTime.of(2023, 1, 1, 10, 0)

        when:
            def actual = visitValidationService.dateIsBeforeOrEqual(requestedVisitDate, testedDate)

        then:
            actual
    }

    def 'should return false if requestedVisitDate is after tested date'() {
        given:
            def requestedVisitDate = LocalDateTime.of(2023, 1, 1, 11, 0)
            def testedDate = LocalDateTime.of(2023, 1, 1, 10, 0)

        when:
            def actual = visitValidationService.dateIsBeforeOrEqual(requestedVisitDate, testedDate)

        then:
            !actual
    }

}

