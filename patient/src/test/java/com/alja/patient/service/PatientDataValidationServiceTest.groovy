package com.alja.patient.service


import com.alja.errors.PatientError
import com.alja.exception.PatientException
import com.alja.patient.model.PatientEntity
import com.alja.patient.model.repository.PatientRepository
import spock.lang.Specification

class PatientDataValidationServiceTest extends Specification {

    PatientDataValidationService patientDataValidationService
    PatientRepository patientRepository = Mock()

    def setup() {
        patientDataValidationService = new PatientDataValidationService(
                patientRepository)
    }

    def 'should not throw exception when social security number does not exist'() {
        given:
            def notExistingSocialSecurityNumber = '12345678910'
            patientRepository.findAllBySocialSecurityNumber(notExistingSocialSecurityNumber) >> []

        when:
            patientDataValidationService.validateSocialSecurityNumber(notExistingSocialSecurityNumber)

        then:
            noExceptionThrown()
    }

    def 'should throw an exception for already existing social security number'() {
        given:
            def existingSocialSecurityNumber = '12345678910'
            patientRepository.findAllBySocialSecurityNumber(existingSocialSecurityNumber) >> [_ as String]

        when:
            def actual = patientDataValidationService.validateSocialSecurityNumber(existingSocialSecurityNumber)

        then:
            actual = thrown(PatientException)

        expect:
            actual != null
            actual instanceof PatientException
            actual.message == PatientError.PATIENT_SOCIAL_SECURITY_NUMBER_ALREADY_EXISTS_ERROR.getMessage()
    }

    def 'should throw exception when patient does not exist'() {
        given:
            def id = "id"
            patientRepository.findPatientEntityByPatientId(id) >> Optional.empty()

        when:
            def actual = patientDataValidationService.findPatientIfPresent(id)

        then:
            actual = thrown(PatientException)

        expect:
            actual != null
            actual instanceof PatientException
            actual.message == PatientError.PATIENT_NOT_FOUND_ERROR.getMessage()
    }

    def 'should not throw exception when patient exist'() {
        given:
            def id = "id"
            patientRepository.findPatientEntityByPatientId(id) >> Optional.of(PatientEntity.builder().build())

        when:
            def actual = patientDataValidationService.findPatientIfPresent(id)

        then:
            noExceptionThrown()
    }

    def 'should not throw exception when phone number does not exist'() {
        given:
            def notExistingPhoneNumber = '123456789'
            patientRepository.findAllByContactDetailsPhoneNumber(notExistingPhoneNumber) >> []

        when:
            patientDataValidationService.validatePhoneNumber(notExistingPhoneNumber)

        then:
            noExceptionThrown()
    }

    def 'should throw an exception for already existing phone number'() {
        given:
            def existingPhoneNumber = '123456789'
            patientRepository.findAllByContactDetailsPhoneNumber(existingPhoneNumber) >> [_ as String]

        when:
            def actual = patientDataValidationService.validatePhoneNumber(existingPhoneNumber)

        then:
            actual = thrown(PatientException)

        expect:
            actual != null
            actual instanceof PatientException
            actual.message == PatientError.PATIENT_PHONE_NUMBER_ALREADY_EXISTS_ERROR.getMessage()
    }

    def 'should throw an exception for already existing email'() {
        given:
            def existingEmail = 'doc@email.com'
            patientRepository.findAllByContactDetailsEmail(existingEmail) >> [_ as String]

        when:
            def actual = patientDataValidationService.validateEmail(existingEmail)

        then:
            actual = thrown(PatientException)

        expect:
            actual != null
            actual instanceof PatientException
            actual.message == PatientError.PATIENT_EMAIL_ALREADY_EXISTS_ERROR.getMessage()
    }

    def 'should throw an exception when patient age is under 18'() {
        given:
            def birthDate = '2015-01-01'

        when:
            def actual = patientDataValidationService.validateAge(birthDate)

        then:
            actual = thrown(PatientException)

        expect:
            actual != null
            actual instanceof PatientException
            actual.message == PatientError.PATIENT_MINOR_ERROR.getMessage()
    }

    def 'should not throw an exception when patient age is over 18'() {
        given:
            def birthDate = '2005-01-01'

        when:
            def actual = patientDataValidationService.validateAge(birthDate)

        then:
            noExceptionThrown()
    }

}
