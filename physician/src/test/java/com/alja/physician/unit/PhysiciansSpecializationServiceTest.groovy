package com.alja.physician.unit

import com.alja.exception.PhysicianException
import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.physician.model.mapper.PhysicianSpecializationMapper
import com.alja.physician.model.repository.PhysicianSpecializationRepository
import com.alja.physician.service.LogService
import com.alja.physician.service.PhysiciansSpecializationService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PhysiciansSpecializationServiceTest extends Specification {

    PhysiciansSpecializationService physiciansSpecializationService
    PhysicianSpecializationRepository physicianSpecializationRepository = Mock()
    PhysicianSpecializationMapper physicianSpecializationMapper = Mock()
    LogService logService = Mock()
    def specializationName = "radiologist"
    def newSpecializationName = "neurologist"

    def setup() {
        physiciansSpecializationService = new PhysiciansSpecializationService(physicianSpecializationRepository,
                physicianSpecializationMapper, logService)
    }

    def "should return a list of specializations"() {
        given:
            def specializationEntity = PhysicianFixtures.createSpecialization(specializationName)
            def dto = PhysicianFixtures.createSpecializationDto(specializationName)

            physicianSpecializationRepository.findAll() >> [specializationEntity]
            physicianSpecializationMapper.specializationEntityToDto(specializationEntity) >> dto

        when:
            def result = physiciansSpecializationService.getAllSpecializations()

        then:
            result.size() == 1
            result[0].specializationName == specializationName
    }

    def "should save a new specialization"() {
        given:
            def specializationDTO = PhysicianFixtures.createSpecializationDto(specializationName)

        when:
            physiciansSpecializationService.addNewSpecialization(specializationDTO)

        then:
            1 * physicianSpecializationRepository.save(_)
            1 * logService.logOperation(_, specializationName)
    }

    def "should update an existing specialization"() {
        given:
            1 * physicianSpecializationRepository.existsBySpecializationName(specializationName) >> true
            1 * physicianSpecializationRepository.existsBySpecializationName(newSpecializationName) >> false
            1 * physicianSpecializationRepository.findBySpecializationName(specializationName) >>
                    PhysicianFixtures.createSpecialization(specializationName)

        when:
            physiciansSpecializationService.updateSpecialization(specializationName, newSpecializationName)

        then:
            1 * logService.logOperation(_, specializationName, newSpecializationName)
            1 * physicianSpecializationRepository.save(_)
    }

    def "should delete an existing specialization"() {
        given:
            1 * physicianSpecializationRepository.existsBySpecializationName(specializationName) >> true

        when:
            physiciansSpecializationService.deleteSpecialization(specializationName)

        then:
            1 * logService.logOperation(_, specializationName)
            1 * physicianSpecializationRepository.deleteBySpecializationName(specializationName)
    }

    def "should throw an exception for non-existing specialization"() {
        given:
            def nonExistingSpecializationName = "NonExistingSpecialization"
            1 * physicianSpecializationRepository.existsBySpecializationName(nonExistingSpecializationName) >> false

        when:
            def actual = physiciansSpecializationService.validateIfEntityExists(nonExistingSpecializationName)

        then:
            actual = thrown(PhysicianException)

        expect:
            actual != null
            actual instanceof PhysicianException
            actual.message == "Physician Specialization does not exist"
    }
}