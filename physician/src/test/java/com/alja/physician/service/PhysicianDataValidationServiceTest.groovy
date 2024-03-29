package com.alja.physician.service

import com.alja.errors.PhysicianError
import com.alja.exception.PhysicianException
import com.alja.physician.model.PhysicianEntity
import com.alja.physician.model.repository.PhysicianRepository
import com.alja.physician.model.repository.PhysicianSpecializationRepository
import spock.lang.Specification

class PhysicianDataValidationServiceTest extends Specification {

    private PhysicianDataValidationService physicianDataValidationService
    private PhysicianRepository physicianRepository = Mock()
    private PhysicianSpecializationRepository physicianSpecializationRepository = Mock()
    private ClientsService clientsService = Mock()

    def setup() {
        physicianDataValidationService = new PhysicianDataValidationService(
                physicianRepository,
                physicianSpecializationRepository,
                clientsService)
    }

    def 'should throw exception when physician does not exist'() {
        given:
            def id = "id"
            physicianRepository.findPhysicianEntityByPhysicianId(id) >> Optional.empty()

        when:
            def actual = physicianDataValidationService.findPhysicianIfPresent(id)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == PhysicianError.PHYSICIAN_NOT_FOUND_ERROR.getMessage()
    }

    def 'should not throw exception when physician exist'() {
        given:
            def id = "id"
            physicianRepository.findPhysicianEntityByPhysicianId(id) >> Optional.of(PhysicianEntity.builder().build())

        when:
            def actual = physicianDataValidationService.findPhysicianIfPresent(id)

        then:
            noExceptionThrown()
    }

    def 'should not throw exception when phone number does not exist'() {
        given:
            def notExistingPhoneNumber = '123456789'
            physicianRepository.findAllByContactDetailsPhoneNumber(notExistingPhoneNumber) >> []

        when:
            physicianDataValidationService.validatePhoneNumber(notExistingPhoneNumber)

        then:
            noExceptionThrown()
    }

    def 'should throw an exception for already existing phone number'() {
        given:
            def existingPhoneNumber = '123456789'
            physicianRepository.findAllByContactDetailsPhoneNumber(existingPhoneNumber) >> [_ as String]

        when:
            def actual = physicianDataValidationService.validatePhoneNumber(existingPhoneNumber)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == PhysicianError.PHYSICIAN_PHONE_NUMBER_ALREADY_EXISTS_ERROR.getMessage()
    }

    def 'should throw an exception for already existing email'() {
        given:
            def existingEmail = 'doc@email.com'
            physicianRepository.findAllByContactDetailsEmail(existingEmail) >> [_ as String]

        when:
            def actual = physicianDataValidationService.validateEmail(existingEmail)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == PhysicianError.PHYSICIAN_EMAIL_ALREADY_EXISTS_ERROR.getMessage()
    }

    def 'should throw an exception for non-existing specialization'() {
        given:
            def nonExistingSpecializationName = 'NonExistingSpecialization'
            1 * physicianSpecializationRepository.existsBySpecializationName(nonExistingSpecializationName) >> false

        when:
            def actual = physicianDataValidationService.validateIfSpecializationExists(nonExistingSpecializationName)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == PhysicianError.PHYSICIAN_SPECIALIZATION_NOT_FOUND_ERROR.getMessage()
    }

    def 'should throw an exception for already existing specialization'() {
        given:
            def existingSpecializationName = 'existingSpecialization'
            1 * physicianSpecializationRepository.existsBySpecializationName(existingSpecializationName) >> true

        when:
            def actual = physicianDataValidationService.validateIfSpecializationAlreadyExists(existingSpecializationName)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == PhysicianError.PHYSICIAN_SPECIALIZATION_ALREADY_EXISTS_ERROR.getMessage()
    }

    def 'should throw exception when physician has visits appointed'() {
        given:
            def id = "id"
            clientsService.hasVisitsAppointed(id) >> true

        when:
            def actual = physicianDataValidationService.validateAppointedVisits(id)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == PhysicianError.PHYSICIAN_APPOINTED_VISITS_ERROR.getMessage()
    }

    def 'should not throw exception when physician has no visits appointed'() {
        given:
            def id = "id"
            clientsService.hasVisitsAppointed(id) >> false

        when:
            physicianDataValidationService.validateAppointedVisits(id)

        then:
            noExceptionThrown()
    }
}
