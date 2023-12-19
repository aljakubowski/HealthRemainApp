package com.alja.physician.model.mapper


import com.alja.physician.fixtures.PhysicianFixtures
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PhysicianMapperTest extends Specification {

    PhysicianMapper physicianMapper

    def setup() {
        physicianMapper = new PhysicianMapperImpl()
    }

    def "should map dto to physician entity"() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def phoneNumber = '123456789'
            def email = 'jand@mymail.com'
            def street = 'Wilcza'
            def houseNumber = '42'
            def postCode = '98-765'
            def city = 'Warszawa'
            def country = 'Polska'
            def physicianDTO = PhysicianFixtures.createPhysicianRegisterDTOCustom(
                    firstName, lastName, physicianSpecialization,
                    phoneNumber, email,
                    street, houseNumber, postCode, city, country
            )
        when:
            def result = physicianMapper.toPhysicianEntity(physicianDTO)
        then:
            result.physicianId != null
            result.firstName == firstName
            result.lastName == lastName
            result.physicianSpecialization == physicianSpecialization
            result.contactDetails.phoneNumber == phoneNumber
            result.contactDetails.email == email
            result.address.street == street
            result.address.houseNumber == houseNumber
            result.address.postCode == postCode
            result.address.city == city
            result.address.country == country

    }
}
