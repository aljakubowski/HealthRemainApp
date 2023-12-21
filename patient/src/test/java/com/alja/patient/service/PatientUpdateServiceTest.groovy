package com.alja.patient.service

import com.alja.patient.fixtures.PatientFixtures
import spock.lang.Specification

import java.time.LocalDate

class PatientUpdateServiceTest extends Specification {

    PatientUpdateService patientUpdateService
    PatientDataValidationService patientDataValidationService = Mock()

    def setup() {
        patientUpdateService = new PatientUpdateService(patientDataValidationService)
    }

    def 'should update or not patient fields'(String firstName, String lastName, String birthDate, String socialSecurityNum,
                                              String newFirstName, String newLastName, String newBirthDate, String newSocialSecurityNum) {
        when:
            def entityToUpdate
                    = PatientFixtures.createPatientWithFields(firstName, lastName, birthDate, socialSecurityNum)

            def updateDTO =
                    PatientFixtures.createPatientUpdateWithFields(newFirstName, newLastName, newBirthDate, newSocialSecurityNum)

        then:
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).firstName == firstNameUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).lastName == lastNameUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).birthDate == LocalDate.parse(birthDateUpdated)
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).socialSecurityNumber == socialSecurityNumUpdated

        where:
            firstName   | lastName   | birthDate    | socialSecurityNum | newFirstName   | newLastName   | newBirthDate | newSocialSecurityNum || firstNameUpdated | lastNameUpdated | birthDateUpdated | socialSecurityNumUpdated
            'firstName' | 'lastName' | '2000-01-01' | '12345678910'     | 'newFirstName' | 'newLastName' | '2005-05-05' | '01987654321'        || 'newFirstName'   | 'newLastName'   | '2005-05-05'     | '01987654321'
            'firstName' | 'lastName' | '2000-01-01' | '12345678910'     | null           | null          | null         | null                 || 'firstName'      | 'lastName'      | '2000-01-01'     | '12345678910'
    }


    def 'should update or not contact details'(String phoneNumber, String email, String newPhoneNumber, String newEmail) {
        when:
            def entityToUpdate
                    = PatientFixtures.createPatientWithContactDetails(phoneNumber, email)

            def updateDTO
                    = PatientFixtures.createPatientUpdateWithContactDetailsUpdate(newPhoneNumber, newEmail)

        then:
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getContactDetails().phoneNumber == numberUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getContactDetails().email == emailUpdated

        where:
            phoneNumber | email             | newPhoneNumber | newEmail             || numberUpdated | emailUpdated
            '987654321' | 'email@email.com' | '123456789'    | 'newEmail@email.com' || '123456789'   | 'newEmail@email.com'
            '987654321' | 'email@email.com' | null           | null                 || '987654321'   | 'email@email.com'

    }


    def 'should update or not address'(String street, String houseNumber, String postCode, String city, String country,
                                       String newStreet, String newHouseNumber, String newPostCode, String newCity, String newCountry) {
        when:
            def entityToUpdate
                    = PatientFixtures.createPatientWithAddress(street, houseNumber, postCode, city, country)

            def updateDTO
                    = PatientFixtures.createPatientUpdateWithAddress(newStreet, newHouseNumber, newPostCode, newCity, newCountry)

        then:
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getAddress().getStreet() == streetUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getAddress().getHouseNumber() == houseNumberUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getAddress().getPostCode() == postCodeUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getAddress().getCity() == cityUpdated
            patientUpdateService.updatePatient(entityToUpdate, updateDTO).getAddress().getCountry() == countryUpdated

        where:

            street   | houseNumber | postCode | city   | country   | newStreet   | newHouseNumber | newPostCode | newCity   | newCountry   || streetUpdated | houseNumberUpdated | postCodeUpdated | cityUpdated | countryUpdated
            'street' | '12'        | '01234'  | 'city' | 'country' | 'newStreet' | '34'           | '56789'     | 'newCity' | 'newCountry' || 'newStreet'   | '34'               | '56789'         | 'newCity'   | 'newCountry'
            'street' | '12'        | '01234'  | 'city' | 'country' | null        | null           | null        | null      | null         || 'street'      | '12'               | '01234'         | 'city'      | 'country'
    }


    def 'should return appropriate boolean'(String value1, String value2) {

        expect:
            patientUpdateService.isDifferent(value1, value2) == result

        where:
            value1   | value2   || result
            'value1' | 'value1' || false
            'value1' | 'value2' || true
    }
}
