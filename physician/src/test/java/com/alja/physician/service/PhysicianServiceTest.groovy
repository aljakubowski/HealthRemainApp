package com.alja.physician.service


import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.physician.model.mapper.PhysicianMapper
import com.alja.physician.model.repository.PhysicianRepository
import spock.lang.Specification

class PhysicianServiceTest extends Specification {

    private PhysicianService physicianService
    private PhysicianDataValidationService physicianDataValidationService = Mock()
    private PhysicianUpdateService physicianUpdateService = Mock()
    private PhysicianRepository physicianRepository = Mock()
    private PhysicianMapper physicianMapper = Mock()
    private LogService logService = Mock()

    def setup() {
        physicianService = new PhysicianService(
                physicianDataValidationService,
                physicianUpdateService,
                physicianRepository,
                physicianMapper,
                logService)
    }

    def 'should register a new physician'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def physicianRegisterDTO = PhysicianFixtures.createPhysicianRegisterDTOCustomWithFields(
                    firstName, lastName, physicianSpecialization)

            physicianMapper.toPhysicianEntity(physicianRegisterDTO) >> PhysicianFixtures.createPhysicianWithFieldsAndUuid(
                    firstName, lastName, physicianSpecialization, UUID.randomUUID().toString())

        when:
            def result = physicianService.registerNewPhysician(physicianRegisterDTO)

        then:
            result != null
            result.physicianId != null
            result.firstName == physicianRegisterDTO.firstName
            result.lastName == physicianRegisterDTO.lastName
            result.physiciansSpecialization == physicianRegisterDTO.physicianSpecialization
    }

    def 'should get all physicians'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def physicianEntity = PhysicianFixtures.createPhysicianWithFieldsAndUuid(
                    firstName, lastName, physicianSpecialization, UUID.randomUUID().toString())

            physicianRepository.findAll() >> [physicianEntity]

        when:
            def result = physicianService.getAllPhysicians()

        then:
            result.size() == 1
            result.get(0).firstName == firstName
            result.get(0).lastName == lastName
            result.get(0).physiciansSpecialization == physicianSpecialization
    }

    def 'should get physician by id with details'() {
        given:
            def physicianId = "id"
            def details = false

            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def phoneNumber = '123456789'
            def email = 'mail@mail.com'
            def street = 'street'
            def houseNumber = 'houseNumber'
            def postCode = 'postCode'
            def city = 'city'
            def country = 'country'

            def physicianEntity = PhysicianFixtures.createPhysicianWithAllFieldsCustom(
                    firstName, lastName, physicianSpecialization, UUID.randomUUID().toString(), phoneNumber, email,
                    street, houseNumber, postCode, city, country)


            physicianDataValidationService.findPhysicianIfPresent(physicianId) >> physicianEntity


        when:
            def result = physicianService.getPhysicianById(physicianId, details)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
            result.physiciansSpecialization == physicianSpecialization
    }

    def 'should update physician'() {
        given:
            def physicianId = "id"

            def firstName = 'Jan'
            def firstNameUpdated = 'Stefan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def physicianUpdateDTO = PhysicianFixtures.createPhysicianUpdateWithFields(
                    firstNameUpdated, lastName, physicianSpecialization)
            def physicianEntityToUpdate
                    = PhysicianFixtures.createPhysicianWithFields(firstName, lastName, physicianSpecialization)
            def physicianEntityUpdated
                    = PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstNameUpdated, lastName,
                    physicianSpecialization, UUID.randomUUID().toString())

            physicianDataValidationService.findPhysicianIfPresent(physicianId) >> physicianEntityToUpdate
            physicianUpdateService.updatePhysician(physicianEntityToUpdate, physicianUpdateDTO) >> physicianEntityUpdated
        when:
            def result = physicianService.updatePhysician(physicianId, physicianUpdateDTO)

        then:
            result != null
            result.firstName == firstNameUpdated
    }

    def "should delete physician by id"() {
        given:
            def physicianId = "id"

            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            physicianDataValidationService.findPhysicianIfPresent(physicianId)
                    >> PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstName, lastName, physicianSpecialization,
                    UUID.randomUUID().toString())

        when:
            def result = physicianService.deletePhysician(physicianId)

        then:
            result != null
            result.firstName == firstName
            result.lastName == lastName
            result.physiciansSpecialization == physicianSpecialization
    }

}
