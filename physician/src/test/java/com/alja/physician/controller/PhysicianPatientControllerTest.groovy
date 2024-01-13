package com.alja.physician.controller

import com.alja.errors.PhysicianError
import com.alja.physician.config.AppIntegrationTest
import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.physician.model.mapper.PhysicianMapper
import com.alja.physician.model.repository.PhysicianRepository
import com.alja.physician.model.repository.PhysicianSpecializationRepository
import com.alja.physician.service.ClientsService
import com.alja.physician.service.PhysicianDataValidationService
import com.alja.physician.service.PhysicianService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

import static com.alja.physician.controller_resources.PhysicianPatientResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PhysicianPatientControllerTest extends AppIntegrationTest {

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

    @Autowired
    private ClientsService clientsService

    def firstName = 'Jan'
    def lastName = 'Dobry'
    def physicianSpecialization = 'ORTHOPEDIST'
    def physicianId = UUID.randomUUID() toString()

    void cleanup() {
        physicianRepository.deleteAll()
        physicianSpecializationRepository.deleteAll()
    }


    def 'should get all Physicians'() {
        given:
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
            physicianRepository.save(PhysicianFixtures.createPhysicianWithFieldsAndUuid(firstName, lastName, physicianSpecialization, physicianId))
            def PHYSICIAN_ID_PATH = "/" + physicianId

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
            def PHYSICIAN_ID_PATH = "/" + physicianId

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PHYSICIAN_ID_PATH))

        then:
            result.andExpect(status().isNotFound())
            result.andExpect(jsonPath('$.message').value(PhysicianError.PHYSICIAN_NOT_FOUND_ERROR.message))
    }

}
