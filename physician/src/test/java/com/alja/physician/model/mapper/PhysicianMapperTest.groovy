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
            result.firstName == firstName
            lastName == result.lastName
            physicianSpecialization == result.physicianSpecialization
            phoneNumber == result.contactDetails.phoneNumber
            email == result.contactDetails.email
            street == result.address.street
            houseNumber == result.address.houseNumber
            postCode == result.address.postCode
            city == result.address.city
            country == result.address.country
    }
}
