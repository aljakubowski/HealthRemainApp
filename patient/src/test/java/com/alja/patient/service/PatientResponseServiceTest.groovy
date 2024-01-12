package com.alja.patient.service


import com.alja.patient.fixtures.PatientFixtures
import com.alja.patient.model.Address
import com.alja.patient.model.ContactDetails
import com.alja.patient.model.mapper.PatientMapper
import com.alja.visit.client.VisitClient
import spock.lang.Specification

import java.time.LocalDate
import java.time.Period

class PatientResponseServiceTest extends Specification {

    private PatientResponseService patientResponseService
    private PatientDataValidationService patientDataValidationService = Mock()
    private PatientMapper patientMapper = Mock()
    private VisitClient visitClient = Mock()

    def setup() {
        patientResponseService = new PatientResponseService(
                patientDataValidationService,
                patientMapper,
                visitClient)
    }

    def 'should return patient simple response'() {
        given:
            def patientId = 'patientId'
            def firstName = 'Michal'
            def lastName = 'Lato'
            def patientEntity
                    = PatientFixtures.createPatientWithNameAndId(firstName, lastName, patientId)

        when:
            def result = patientResponseService.getPatientResponseSimple(patientEntity)

        then:
            result != null
            result.patientId == patientId
            result.firstName == firstName
            result.lastName == lastName
    }

    def 'should return patient response with details'() {
        given:
            def firstName = 'Michal'
            def lastName = 'Lato'
            def birthDate = '2000-01-01'
            def socialSecurityNum = '12345678910'
            def patientId = UUID.randomUUID().toString()
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def street = 'Wilcza'
            def houseNumber = '42'
            def postCode = '98-765'
            def city = 'Warszawa'
            def country = 'Polska'

            def dateNow = LocalDate.now()
            def age = Period.between(LocalDate.parse(birthDate), dateNow).getYears();

            patientDataValidationService.calculateAge(_ as String) >> age
            patientMapper.contactDetailsToDto(_ as ContactDetails) >> PatientFixtures.createContactDetailsDTOCustom(phoneNumber, email)
            patientMapper.addressToDto(_ as Address) >> PatientFixtures.createAddressDTOCustom(street, houseNumber, postCode, city, country)

            def patientEntity
                    = PatientFixtures.createPatientWithAllFieldsCustom(firstName, lastName, birthDate, socialSecurityNum,
                    patientId, phoneNumber, email, street, houseNumber, postCode, city, country)

        when:
            def result = patientResponseService.getPatientResponseDetailed(patientEntity)

        then:
            result != null
            result.patientId == patientId
            result.firstName == firstName
            result.lastName == lastName
            result.birthDate == birthDate
            result.age == age
            result.socialSecurityNumber == socialSecurityNum
            result.contactDetails.phoneNumber == phoneNumber
            result.contactDetails.email == email
            result.address.street == street
            result.address.houseNumber == houseNumber
            result.address.postCode == postCode
            result.address.city == city
            result.address.country == country
    }


    def 'should return patient response with visits'() {
        given:
            def visitId = 'visitId'
            def visitSimpleResponse = PatientFixtures.createVisitSimpleResponseDTOWithVisitId(visitId)

            patientResponseService.getVisitsIds(_ as String) >> [visitId]
            visitClient.getAllPatientVisits(_ as String) >> [visitSimpleResponse]

            def patientId = 'patientId'
            def firstName = 'Michal'
            def lastName = 'Lato'
            def visits = [visitId]

            def patientEntity
                    = PatientFixtures.createPatientWithFieldsAndVisits(patientId, firstName, lastName, visits)

        when:
            def result = patientResponseService.getPatientResponseWithVisits(patientEntity)

        then:
            result != null
            result.patientId == patientId
            result.firstName == firstName
            result.lastName == lastName
            result.visitsId.get(0) == visitId
            result.visitsId.size() == 1
    }

    def 'should return list of visit ids'() {
        given:
            def visitId = 'visitId'
            def visitSimpleResponse = PatientFixtures.createVisitSimpleResponseDTOWithVisitId(visitId)
            visitClient.getAllPatientVisits(_ as String) >> [visitSimpleResponse]

        when:
            def result = patientResponseService.getVisitsIds(_ as String)

        then:
            result != null
            result.size() == 1
    }

}
