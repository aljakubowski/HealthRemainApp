package com.alja.patient.service

import com.alja.patient.dto.PatientResponseDetailedDTO
import com.alja.patient.dto.PatientResponseVisitsDTO
import com.alja.patient.fixtures.PatientFixtures
import com.alja.patient.model.mapper.PatientMapper
import com.alja.patient.model.repository.PatientRepository
import spock.lang.Specification

import java.time.LocalDate

class PatientServiceTest extends Specification {

    PatientService patientService
    PatientDataValidationService patientDataValidationService = Mock()
    PatientUpdateService patientUpdateService = Mock()
    PatientRepository patientRepository = Mock()
    PatientMapper patientMapper = Mock()
    LogService logService = Mock()

    def setup() {
        patientService = new PatientService(
                patientDataValidationService,
                patientUpdateService,
                patientRepository,
                patientMapper,
                logService)
    }

    def 'should register a new patient'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientRegisterDTO = PatientFixtures.createPatientRegisterDTOCustomWithFields(
                    firstName, lastName, birthDate, socialSecurityNum)

            patientMapper.toPatientEntity(patientRegisterDTO) >> PatientFixtures.createPatientWithFieldsAndUuid(
                    firstName, lastName, birthDate, socialSecurityNum, UUID.randomUUID().toString())

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
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientEntity = PatientFixtures.createPatientWithFieldsAndUuid(
                    firstName, lastName, birthDate, socialSecurityNum, UUID.randomUUID().toString())

            patientRepository.findAll() >> [patientEntity]

        when:
            def result = patientService.getAllPatients()

        then:
            result.size() == 1
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

        when:
            PatientResponseDetailedDTO result = patientService.getPatientById(patientId.toString(), dataFormat)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
            result.patientId == patientId.toString()
            result.birthDate == LocalDate.parse(birthDate)
            result.socialSecurityNumber == socialSecurityNum
            result.age == age
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

            def patientEntity
                    = PatientFixtures.createPatientWithFieldsAndUuid(
                    firstName, lastName, birthDate, socialSecurityNum, patientId)
            //todo to update with visit
            patientEntity.setVisitsId(List.of())

            patientDataValidationService.findPatientIfPresent(patientId.toString()) >> patientEntity

        when:
            PatientResponseVisitsDTO result = patientService.getPatientById(patientId.toString(), dataFormat)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
            result.patientId == patientId.toString()
            result.visitsId != null
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

        when:
            def result = patientService.deletePatient(patientId)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
    }
}
