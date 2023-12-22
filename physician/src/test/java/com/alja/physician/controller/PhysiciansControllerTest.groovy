package com.alja.physician.controller

import com.alja.errors.PhysicianError
import com.alja.physician.config.AppIntegrationTest
import com.alja.physician.dto.PhysicianRegisterDTO
import com.alja.physician.dto.PhysicianUpdateDTO
import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.physician.model.mapper.PhysicianMapper
import com.alja.physician.model.repository.PhysicianRepository
import com.alja.physician.model.repository.PhysicianSpecializationRepository
import com.alja.physician.service.PhysicianDataValidationService
import com.alja.physician.service.PhysicianService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static com.alja.physician.controller_resources.PhysiciansResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PhysiciansControllerTest extends AppIntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private PhysicianService physicianService

    @Autowired
    private PhysicianRepository physicianRepository

    @Autowired
    private PhysicianSpecializationRepository physicianSpecializationRepository

    @Autowired
    private PhysicianMapper physicianMapper;

    @Autowired
    private PhysicianDataValidationService physicianDataValidationService

    @Autowired
    private ObjectMapper objectMapper

    void cleanup() {
        physicianRepository.deleteAll()
        physicianSpecializationRepository.deleteAll()
    }

    def 'should register new Physician'() {
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

            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(physicianSpecialization))
            PhysicianRegisterDTO physicianRegisterDTO
                    = PhysicianFixtures.createPhysicianRegisterDTOCustom(firstName, lastName, physicianSpecialization,
                    phoneNumber, email, street, houseNumber, postCode, city, country)

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(physicianRegisterDTO)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.physiciansSpecialization').value(physicianSpecialization))
    }

    def 'should not register new Physician when phoneNumber or email already exists'() {
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

            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(physicianSpecialization))
            physicianRepository.save(PhysicianFixtures.createPhysicianWithContactDetails(phoneNumber, email))

            PhysicianRegisterDTO physicianRegisterDTO
                    = PhysicianFixtures.createPhysicianRegisterDTOCustom(firstName, lastName, physicianSpecialization,
                    phoneNumber, email, street, houseNumber, postCode, city, country)

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(physicianRegisterDTO)))
        then:
            result.andExpect(status().isBadRequest())
            result.andExpect(jsonPath('$.message').value(PhysicianError.PHYSICIAN_PHONE_NUMBER_ALREADY_EXISTS_ERROR.message))
    }


    def 'should get all Physicians'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def physicianId = UUID.randomUUID() toString()
            physicianRepository.save(PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstName, lastName, physicianSpecialization, physicianId))

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.[0].firstName').value(firstName))
            result.andExpect(jsonPath('$.[0].lastName').value(lastName))
            result.andExpect(jsonPath('$.[0].physiciansSpecialization').value(physicianSpecialization))
            result.andExpect(jsonPath('$.[0].physicianId').value(physicianId))
    }

    def 'should get Physician by id'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def physicianId = UUID.randomUUID().toString()
            physicianRepository.save(PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstName, lastName, physicianSpecialization, physicianId))

            def details = false
            def PHYSICIAN_ID_PATH = "/" + physicianId + "/" + details

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PHYSICIAN_ID_PATH))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.physiciansSpecialization').value(physicianSpecialization))
            result.andExpect(jsonPath('$.physicianId').value(physicianId))
    }

    def 'should not get Physician by id when not exists'() {
        given:
            def physicianId = UUID.randomUUID().toString()

            def details = false
            def PHYSICIAN_ID_PATH = "/" + physicianId + "/" + details

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PHYSICIAN_ID_PATH))

        then:
            result.andExpect(status().isNotFound())
            result.andExpect(jsonPath('$.message').value(PhysicianError.PHYSICIAN_NOT_FOUND_ERROR.message))
    }


    def 'should update Physician'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def firstNameUpdated = 'John'
            def lastNameUpdated = 'Good'
            def physicianId = UUID.randomUUID().toString()

            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(physicianSpecialization))
            physicianRepository.save(PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstName, lastName, physicianSpecialization, physicianId))

            def PHYSICIAN_ID_PATH = "/" + physicianId
            PhysicianUpdateDTO physicianUpdateDTO
                    = PhysicianFixtures.createPhysicianUpdateWithFields(firstNameUpdated, lastNameUpdated, physicianSpecialization)

        when:
            def result = this.mockMvc.perform(put(RESOURCE_PATH + PHYSICIAN_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(physicianUpdateDTO)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstNameUpdated))
            result.andExpect(jsonPath('$.lastName').value(lastNameUpdated))
            result.andExpect(jsonPath('$.physiciansSpecialization').value(physicianSpecialization))
            result.andExpect(jsonPath('$.physicianId').value(physicianId))
    }

    def 'should delete Physician by id'() {
        given:
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'ORTHOPEDIST'
            def physicianId = UUID.randomUUID().toString()

            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(physicianSpecialization))
            physicianRepository.save(PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstName, lastName, physicianSpecialization, physicianId))

            def PHYSICIAN_ID_PATH = "/" + physicianId

        when:
            def result = this.mockMvc.perform(delete(RESOURCE_PATH + PHYSICIAN_ID_PATH))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.firstName').value(firstName))
            result.andExpect(jsonPath('$.lastName').value(lastName))
            result.andExpect(jsonPath('$.physiciansSpecialization').value(physicianSpecialization))
            result.andExpect(jsonPath('$.physicianId').value(physicianId))
    }

    def 'should not delete Physician by id when not exists'() {
        given:
            def physicianId = UUID.randomUUID().toString()

            def PHYSICIAN_ID_PATH = "/" + physicianId

        when:
            def result = this.mockMvc.perform(delete(RESOURCE_PATH + PHYSICIAN_ID_PATH))

        then:
            result.andExpect(status().isNotFound())
            result.andExpect(jsonPath('$.message').value(PhysicianError.PHYSICIAN_NOT_FOUND_ERROR.message))
    }

}
