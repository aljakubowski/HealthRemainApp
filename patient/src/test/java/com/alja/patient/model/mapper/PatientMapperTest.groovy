package com.alja.patient.model.mapper

import com.alja.patient.fixtures.PatientFixtures
import spock.lang.Specification

import java.time.LocalDate

class PatientMapperTest extends Specification {
    PatientMapper patientMapper

    def setup() {
        patientMapper = new PatientMapperImpl()
    }

    def "should map dto to patient entity"() {
        given:
            def firstName = 'Michał'
            def lastName = 'Lato'
            def birthDate = '1990-10-20'
            def socialSecurityNumber = '12345678910'
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def street = 'Wilcza'
            def houseNumber = '42'
            def postCode = '98-765'
            def city = 'Warszawa'
            def country = 'Polska'
            def patientDTO = PatientFixtures.createPatientRegisterDTOCustom(
                    firstName, lastName, birthDate, socialSecurityNumber,
                    phoneNumber, email,
                    street, houseNumber, postCode, city, country
            )
        when:
            def result = patientMapper.toPatientEntity(patientDTO)
        then:
            result.patientId != null
            result.firstName == firstName
            result.lastName == lastName
            result.birthDate == LocalDate.parse(birthDate)
            result.socialSecurityNumber == socialSecurityNumber
            result.contactDetails.phoneNumber == phoneNumber
            result.contactDetails.email == email
            result.address.street == street
            result.address.houseNumber == houseNumber
            result.address.postCode == postCode
            result.address.city == city
            result.address.country == country
    }

    def "should map Contact details to dto"() {
        given:
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def contactDetails = PatientFixtures.createContactDetailsCustom(phoneNumber, email)
        when:
            def result = patientMapper.contactDetailsToDto(contactDetails)
        then:
            result.phoneNumber == phoneNumber
            result.email == email
    }
}
