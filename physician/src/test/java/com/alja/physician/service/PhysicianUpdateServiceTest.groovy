package com.alja.physician.service

import com.alja.physician.fixtures.PhysicianFixtures
import spock.lang.Specification

class PhysicianUpdateServiceTest extends Specification {

    PhysicianUpdateService physicianUpdateService
    PhysicianDataValidationService physicianDataValidationService = Mock()

    def setup() {
        physicianUpdateService = new PhysicianUpdateService(physicianDataValidationService)
    }

    def 'should update or not physician fields'(String firstName, String lastName, String specialization,
                                                String newFirstName, String newLastName, String newSpecialization) {
        when:
            def entityToUpdate
                    = PhysicianFixtures.createPhysicianWithFields(firstName, lastName, specialization)

            def updateDTO =
                    PhysicianFixtures.createPhysicianUpdateWithFields(newFirstName, newLastName, newSpecialization)

        then:
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).firstName == firstNameUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).lastName == lastNameUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).physicianSpecialization == specializationUpdated

        where:
            firstName   | lastName   | specialization   | newFirstName   | newLastName   | newSpecialization   || firstNameUpdated | lastNameUpdated | specializationUpdated
            'firstName' | 'lastName' | 'specialization' | 'newFirstName' | 'newLastName' | 'newSpecialization' || 'newFirstName'   | 'newLastName'   | 'newSpecialization'
            'firstName' | 'lastName' | 'specialization' | null           | null          | null                || 'firstName'      | 'lastName'      | 'specialization'
    }


    def 'should update or not contact details'(String phoneNumber, String email, String newPhoneNumber, String newEmail) {
        when:
            def entityToUpdate
                    = PhysicianFixtures.createPhysicianWithContactDetails(phoneNumber, email)

            def updateDTO
                    = PhysicianFixtures.createPhysicianUpdateWithContactDetailsUpdate(newPhoneNumber, newEmail)

        then:
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getContactDetails().phoneNumber == numberUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getContactDetails().email == emailUpdated

        where:
            phoneNumber | email             | newPhoneNumber | newEmail             || numberUpdated | emailUpdated
            '987654321' | 'email@email.com' | '123456789'    | 'newEmail@email.com' || '123456789'   | 'newEmail@email.com'
            '987654321' | 'email@email.com' | null           | null                 || '987654321'   | 'email@email.com'

    }


    def 'should update or not address'(String street, String houseNumber, String postCode, String city, String country,
                                       String newStreet, String newHouseNumber, String newPostCode, String newCity, String newCountry) {
        when:
            def entityToUpdate
                    = PhysicianFixtures.createPhysicianWithAddress(street, houseNumber, postCode, city, country)

            def updateDTO
                    = PhysicianFixtures.createPhysicianUpdateWithAddress(newStreet, newHouseNumber, newPostCode, newCity, newCountry)

        then:
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getAddress().getStreet() == streetUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getAddress().getHouseNumber() == houseNumberUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getAddress().getPostCode() == postCodeUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getAddress().getCity() == cityUpdated
            physicianUpdateService.updatePhysician(entityToUpdate, updateDTO).getAddress().getCountry() == countryUpdated

        where:

            street   | houseNumber | postCode | city   | country   | newStreet   | newHouseNumber | newPostCode | newCity   | newCountry   || streetUpdated | houseNumberUpdated | postCodeUpdated | cityUpdated | countryUpdated
            'street' | '12'        | '01234'  | 'city' | 'country' | 'newStreet' | '34'           | '56789'     | 'newCity' | 'newCountry' || 'newStreet'   | '34'               | '56789'         | 'newCity'   | 'newCountry'
            'street' | '12'        | '01234'  | 'city' | 'country' | null        | null           | null        | null      | null         || 'street'      | '12'               | '01234'         | 'city'      | 'country'
    }


    def 'should return appropriate boolean'(String value1, String value2) {

        expect:
            physicianUpdateService.isDifferent(value1, value2) == result

        where:
            value1   | value2   || result
            'value1' | 'value1' || false
            'value1' | 'value2' || true
    }

}
