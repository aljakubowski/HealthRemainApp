package com.alja.patient.service

import com.alja.patient.dto.PatientResponseDetailedDTO
import com.alja.patient.dto.PatientResponseVisitsDTO
import com.alja.patient.fixtures.PatientFixtures
import com.alja.patient.model.PatientEntity
import com.alja.patient.model.mapper.PatientMapper
import com.alja.patient.model.repository.PatientRepository
import spock.lang.Specification

class PatientServiceTest extends Specification {

    private PatientService patientService
    private PatientDataValidationService patientDataValidationService = Mock()
    private PatientUpdateService patientUpdateService = Mock()
    private PatientResponseService patientResponseService = Mock()
    private PatientRepository patientRepository = Mock()
    private PatientMapper patientMapper = Mock()
    private LogService logService = Mock()

    def setup() {
        patientService = new PatientService(
                patientDataValidationService,
                patientUpdateService,
                patientResponseService,
                patientRepository,
                patientMapper,
                logService)
    }

    def 'should register a new patient'() {
        given:
            def patientId = UUID.randomUUID().toString()
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientRegisterDTO = PatientFixtures.createPatientRegisterDTOCustomWithFields(
                    firstName, lastName, birthDate, socialSecurityNum)

            patientMapper.toPatientEntity(patientRegisterDTO) >> PatientFixtures.createPatientWithFieldsAndUuid(
                    firstName, lastName, birthDate, socialSecurityNum, patientId)


            def patientSimpleResponse
                    = PatientFixtures.createPatientResponseSimple(patientId, firstName, lastName)
            patientResponseService.getPatientResponseSimple(_ as PatientEntity) >> patientSimpleResponse

        when:
            def result = patientService.registerNewPatient(patientRegisterDTO)

        then:
            result != null
            result.patientId != null
            result.firstName == patientRegisterDTO.firstName
            result.lastName == patientRegisterDTO.lastName
    }

    def 'should get all patients'() {
        given:
            def patientId = UUID.randomUUID().toString()
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientEntity = PatientFixtures.createPatientWithFieldsAndUuid(
                    firstName, lastName, birthDate, socialSecurityNum, patientId)

            patientRepository.findAll() >> [patientEntity]

            def patientSimpleResponse
                    = PatientFixtures.createPatientResponseSimple(patientId, firstName, lastName)
            patientResponseService.getPatientResponseSimple(_ as PatientEntity) >> patientSimpleResponse

        when:
            def result = patientService.getAllPatients()

        then:
            result.size() == 1
            result.get(0).patientId == patientId
            result.get(0).firstName == firstName
            result.get(0).lastName == lastName
    }

    def 'should get patient by id with details'() {
        given:
            def dataFormat = "DETAILS"

            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID().toString()
            def phoneNumber = '123456789'
            def email = 'mail@mail.com'
            def street = 'street'
            def houseNumber = 'houseNumber'
            def postCode = 'postCode'
            def city = 'city'
            def country = 'country'
            def age = 20

            def patientEntity = PatientFixtures.createPatientWithAllFieldsCustom(
                    firstName, lastName, birthDate, socialSecurityNum, patientId, phoneNumber, email,
                    street, houseNumber, postCode, city, country)

            patientDataValidationService.findPatientIfPresent(patientId.toString()) >> patientEntity
            patientDataValidationService.calculateAge(birthDate) >> age
            patientMapper.contactDetailsToDto(patientEntity.getContactDetails())
                    >> PatientFixtures.createContactDetailsDTOCustom(phoneNumber, email)
            patientMapper.addressToDto(patientEntity.getAddress())
                    >> PatientFixtures.createAddressDTOCustom(street, houseNumber, postCode, city, country)

            def patientDetailedResponse
                    = PatientFixtures.createPatientResponseWithAllFieldsCustom(
                    firstName, lastName, birthDate, socialSecurityNum, patientId, phoneNumber, email, street, houseNumber,
                    postCode, city, country)
            patientResponseService.returnAppropriateResponse(patientEntity, dataFormat) >> patientDetailedResponse

        when:
            PatientResponseDetailedDTO result = patientService.getPatientById(patientId.toString(), dataFormat)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
            result.patientId == patientId.toString()
            result.birthDate == birthDate
            result.socialSecurityNumber == socialSecurityNum
            result.address.street == street
            result.address.houseNumber == houseNumber
            result.address.postCode == postCode
            result.address.city == city
            result.address.country == country
            result.contactDetails.phoneNumber == phoneNumber
            result.contactDetails.email == email
    }

    def 'should get patient by id with visits'() {
        given:
            def dataFormat = "VISITS"
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID().toString()
            def visitId = 'visitId'
            def visits = [visitId]

            def patientEntity
                    = PatientFixtures.createPatientWithFieldsAndUuid(
                    firstName, lastName, birthDate, socialSecurityNum, patientId)

            def patientVisitsResponse
                    = PatientFixtures.createPatientResponseWithVisits(patientId, firstName, lastName, visits)
            patientResponseService.returnAppropriateResponse(_ as PatientEntity, _ as String) >> patientVisitsResponse

            patientDataValidationService.findPatientIfPresent(patientId.toString()) >> patientEntity

        when:
            PatientResponseVisitsDTO result = patientService.getPatientById(patientId.toString(), dataFormat)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
            result.patientId == patientId.toString()
            result.visitsId != null
            result.visitsId.size() == 1
    }

    def 'should update patient'() {
        given:
            def patientId = "id"
            def firstName = 'Michal'
            def firstNameUpdated = 'Michael'
            def lastName = 'Lato'
            def lastNameUpdated = 'Summer'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientUpdateDTO = PatientFixtures.createPatientUpdateWithFields(
                    firstNameUpdated, lastName, birthDate, socialSecurityNum)
            def patientEntityToUpdate
                    = PatientFixtures.createPatientWithFields(firstName, lastName, birthDate, socialSecurityNum)
            def patientEntityUpdated
                    = PatientFixtures.createPatientWithFieldsAndUuid(firstNameUpdated, lastNameUpdated,
                    birthDate, socialSecurityNum, UUID.randomUUID().toString())

            def patientSimpleResponse
                    = PatientFixtures.createPatientResponseSimple(patientId, firstNameUpdated, lastNameUpdated)
            patientResponseService.getPatientResponseSimple(_ as PatientEntity) >> patientSimpleResponse

            patientDataValidationService.findPatientIfPresent(patientId) >> patientEntityToUpdate
            patientUpdateService.updatePatient(patientEntityToUpdate, patientUpdateDTO) >> patientEntityUpdated
        when:
            def result = patientService.updatePatient(patientId, patientUpdateDTO)

        then:
            result != null
            result.firstName == firstNameUpdated
            result.lastName == lastNameUpdated
    }

    def "should delete patient by id"() {
        given:
            def patientId = "id"
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            patientDataValidationService.findPatientIfPresent(patientId)
                    >> PatientFixtures.createPatientWithFieldsAndUuid(firstName, lastName, birthDate, socialSecurityNum,
                    UUID.randomUUID().toString())

            def patientSimpleResponse
                    = PatientFixtures.createPatientResponseSimple(patientId, firstName, lastName)
            patientResponseService.getPatientResponseSimple(_ as PatientEntity) >> patientSimpleResponse

        when:
            def result = patientService.deletePatient(patientId)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
    }
}
