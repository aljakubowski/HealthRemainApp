package com.alja.visit.service

import com.alja.common.enums.VisitStatus
import com.alja.errors.VisitError
import com.alja.exception.VisitException
import com.alja.visit.fixtures.VisitFixtures
import spock.lang.Specification

import java.time.LocalDateTime

class VisitUpdateServiceTest extends Specification {

    private VisitUpdateService visitUpdateService
    private ClientsService clientsService = Mock()
    private VisitValidationService visitValidationService = Mock()

    def setup() {
        visitUpdateService = new VisitUpdateService(clientsService, visitValidationService)
    }

    def 'should validate and update visit date'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = 'patientId'
            def physicianSpecialization = 'radiologist'

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatus(visitDateFrom, visitDateTo, visitStatus)
            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithDateAndIds(visitDateFrom, physicianId, patientId)
            def physicianResponse
                    = VisitFixtures.createPhysicianResponseWithSpecialization(physicianId, physicianSpecialization)

            clientsService.getPhysicianResponseDTO(_ as String) >> physicianResponse

        when:
            visitUpdateService.validateAndUpdateVisitDate(visitToUpdate, visitUpdateDTO)

        then:
            2 * visitValidationService.getVisitEndDate(visitUpdateDTO.getVisitDate()) >> visitEndDate
            2 * visitValidationService.validateDateWithStatus(visitStatus, visitDateFrom, visitEndDate)
    }

    def 'should update visit date, physician, and patient'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = 'patientId'
            def physicianSpecialization = 'radiologist'

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatus(visitDateFrom, visitDateTo, visitStatus)
            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithDateAndIds(visitDateFrom, physicianId, patientId)
            def physicianResponse
                    = VisitFixtures.createPhysicianResponseWithSpecialization(physicianId, physicianSpecialization)

            clientsService.getPhysicianResponseDTO(_ as String) >> physicianResponse

        when:
            visitUpdateService.updateVisitDateAndPhysicianAndPatient(visitToUpdate, visitUpdateDTO)

        then:
            1 * visitValidationService.getVisitEndDate(visitUpdateDTO.getVisitDate()) >> visitEndDate
            1 * visitValidationService.validateDateWithStatus(visitStatus, visitDateFrom, visitEndDate)
    }

    def 'should update visit date and physician'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = 'patientId'
            def physicianSpecialization = 'radiologist'

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatusAndPhysician(visitDateFrom, visitDateTo, visitStatus, physicianId)
            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithDateAndIds(visitDateFrom, physicianId, patientId)

            def physicianResponse
                    = VisitFixtures.createPhysicianResponseWithSpecialization(physicianId, physicianSpecialization)

            clientsService.getPhysicianResponseDTO(_ as String) >> physicianResponse

        when:
            visitUpdateService.updateVisitDateAndPhysician(visitToUpdate, visitUpdateDTO)

        then:
            1 * visitValidationService.getVisitEndDate(visitUpdateDTO.getVisitDate()) >> visitEndDate
            1 * visitValidationService.validateDateWithStatus(visitStatus, visitDateFrom, visitEndDate)
    }

    def 'should update visit date and patient'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = 'patientId'

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatus(visitDateFrom, visitDateTo, visitStatus)
            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithDateAndIds(visitDateFrom, physicianId, patientId)


        when:
            visitUpdateService.updateVisitDateAndPatient(visitToUpdate, visitUpdateDTO)

        then:
            1 * visitValidationService.getVisitEndDate(visitUpdateDTO.getVisitDate()) >> visitEndDate
            1 * visitValidationService.validateDateWithStatus(visitStatus, visitDateFrom, visitEndDate)

    }

    def 'should update visit date'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = 'patientId'

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatus(visitDateFrom, visitDateTo, visitStatus)
            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithDateAndIds(visitDateFrom, physicianId, patientId)

        when:
            visitUpdateService.updateVisitDate(visitToUpdate, visitUpdateDTO)

        then:
            1 * visitValidationService.getVisitEndDate(visitUpdateDTO.getVisitDate()) >> visitEndDate
            1 * visitValidationService.validateDateWithStatus(visitStatus, visitDateFrom, visitEndDate)
    }

    def 'should update physician id and specialization'() {
        given:
            def physicianId = 'physicianId'
            def updatePhysicianId = 'updatePhysicianId'
            def physicianSpecialization = 'radiologist'

            def visitToUpdate = VisitFixtures.createVisitEntityWithPhysician(physicianId)
            def physicianResponse
                    = VisitFixtures.createPhysicianResponseWithSpecialization(updatePhysicianId, physicianSpecialization)
            def visitUpdateDTO = VisitFixtures.createVisitUpdateDtoWithPhysician(updatePhysicianId)

            clientsService.getPhysicianResponseDTO(_ as String) >> physicianResponse

        when:
            visitUpdateService.updatePhysician(visitToUpdate, visitUpdateDTO)

        then:
            visitToUpdate.getPhysicianId() == updatePhysicianId
            visitToUpdate.getPhysicianSpecialization() == physicianSpecialization
    }

    def 'should update patient id'() {
        given:
            def patientId = 'patientId'
            def updatedPatientId = 'updatePatientId'
            def visitToUpdate = VisitFixtures.createVisitEntityWithPatient(patientId)
            def visitUpdateDTO = VisitFixtures.createVisitUpdateDtoWithPatient(updatedPatientId)

        when:
            visitUpdateService.updatePatient(visitToUpdate, visitUpdateDTO)

        then:
            visitToUpdate.getPatientId() == updatedPatientId
    }

    def 'should invoke methods to validate availability and status'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)
            def visitEndDate = LocalDateTime.of(2020, 1, 1, 12, 30)
            def physicianId = 'physicianId'
            def patientId = 'patientId'

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatus(visitDateFrom, visitDateTo, visitStatus)
            def visitUpdateDTO
                    = VisitFixtures.createVisitUpdateDtoWithDateAndIds(visitDateFrom, physicianId, patientId)

        when:
            visitUpdateService.validateAvailabilityStatusAndUpdate(visitToUpdate, visitUpdateDTO)

        then:
            1 * visitValidationService.validatePhysicianAvailability(_ as String, _ as LocalDateTime)
            1 * visitValidationService.validatePatientAvailability(_ as String, _ as LocalDateTime)
            1 * visitValidationService.getVisitEndDate(_ as LocalDateTime) >> visitEndDate
            1 * visitValidationService.validateDateWithStatus(_, _ as LocalDateTime, _ as LocalDateTime)
            1 * visitValidationService.validateDateRange(_ as LocalDateTime, _ as LocalDateTime)
    }

    def 'should invoke methods to validate date and status'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitDateFrom = LocalDateTime.of(2020, 1, 1, 12, 0)
            def visitDateTo = LocalDateTime.of(2020, 1, 1, 13, 0)

            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithDatesAndStatus(visitDateFrom, visitDateTo, visitStatus)
            def visitUpdateDTO = VisitFixtures.createVisitUpdateDtoWithDate(visitDateFrom)

        when:
            visitUpdateService.validateVisitDateWithStatus(visitToUpdate, visitUpdateDTO, visitDateTo)

        then:
            1 * visitValidationService.validateDateWithStatus(visitStatus, visitDateFrom, visitDateTo)
            1 * visitValidationService.validateDateRange(visitDateFrom, visitDateTo)
    }

    def 'should return true when status is not null'(String visitStatus) {
        expect:
            visitUpdateService.statusToBeUpdated(visitStatus) == result

        where:
            visitStatus          || result
            ' '                  || false
            ''                   || false
            VisitStatus.RESERVED || true
    }

    def 'should not throw exception when updating visit to Available when patient is not enrolled'() {
        given:
            def patientId = ''
            def visitStatusToUpdate = VisitStatus.RESERVED
            def visitStatus = VisitStatus.AVAILABLE
            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithPatientIdAndStatus(patientId, visitStatusToUpdate)

        when:
            visitUpdateService.updateVisitStatus(visitToUpdate, visitStatus)

        then:
            noExceptionThrown()
            visitToUpdate.getVisitStatus() == visitStatus
            1 * visitValidationService.validateDateWithStatus(_, _ as LocalDateTime, _ as LocalDateTime)
    }

    def 'should throw exception when updating visit to Available when patient is enrolled'() {
        given:
            def patientId = 'id'
            def visitStatusToUpdate = VisitStatus.RESERVED
            def visitStatus = VisitStatus.AVAILABLE
            def visitToUpdate
                    = VisitFixtures.createVisitEntityWithPatientIdAndStatus(patientId, visitStatusToUpdate)

        when:
            def actual = visitUpdateService.updateVisitStatus(visitToUpdate, visitStatus)

        then:
            actual = thrown(VisitException)

        expect:
            actual != null
            actual instanceof VisitException
            actual.message == VisitError.VISIT_INVALID_AVAILABLE_STATUS_ERROR.getMessage()
    }

    def 'should return appropriate VisitStatus from string value'(String stringStatus) {
        expect:
            visitUpdateService.getStatus(stringStatus) == result

        where:
            stringStatus || result
            'available'  || VisitStatus.AVAILABLE
            'reSERVED'   || VisitStatus.RESERVED
            'COMPLETED'  || VisitStatus.COMPLETED
            'unrealizeD' || VisitStatus.UNREALIZED
    }

}
