package com.alja.physician.controller


import com.alja.physician.config.AppIntegrationTest
import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.physician.model.mapper.PhysicianSpecializationMapper
import com.alja.physician.model.repository.PhysicianSpecializationRepository
import com.alja.physician.service.PhysiciansSpecializationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static com.alja.physician.controller_resources.PhysiciansSpecializationResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PhysiciansSpecializationControllerTest extends AppIntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private PhysiciansSpecializationService physiciansSpecializationService

    @Autowired
    private PhysicianSpecializationRepository physicianSpecializationRepository

    @Autowired
    private PhysicianSpecializationMapper physicianSpecializationMapper

    @Autowired
    private ObjectMapper objectMapper

    void cleanup() {
        physicianSpecializationRepository.deleteAll()
    }

    def "should get all specializations"() {
        given:
            def specializationName = 'Radiologist'
            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(specializationName))

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.[0].specializationName').value(specializationName))
    }

    def "should add new specialization"() {
        given:
            def specializationName = 'Neurologist'
            def specializationDTO = PhysicianFixtures.createSpecializationDto(specializationName)

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(specializationDTO)))

        then:
            result.andExpect(status().isCreated())
            physicianSpecializationRepository.findAll().size() == 1
    }

    def "should update specialization name"() {
        given:
            def specializationName = 'Neurologist'
            def specializationNewName = 'Radiologist'
            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(specializationName))

        when:
            def result = this.mockMvc.perform(put(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("specializationName", specializationName)
                    .param("specializationNewName", specializationNewName))

        then:
            result.andExpect(status().isOk())
            physicianSpecializationRepository.findAll().size() == 1
            physicianSpecializationRepository.findAll().get(0).getSpecializationName() == specializationNewName
    }

    def "should delete specialization"() {
        given:
            def specializationName = 'Neurologist'
            physicianSpecializationRepository.save(PhysicianFixtures.createSpecialization(specializationName))

        when:
            def result = this.mockMvc.perform(delete(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("specializationName", specializationName))

        then:
            result.andExpect(status().isOk())
            physicianSpecializationRepository.findAll().size() == 0
    }

    def "should return an error when Physician Specialization name is null"(String invalidSpecializationName) {
        given:
            def specializationDTO = PhysicianFixtures.createSpecializationDto(invalidSpecializationName)

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(specializationDTO)))

        then:
            result.andExpect(status().isBadRequest())
        where:
            invalidSpecializationName | _
            ''                        | _
            ' '                       | _
    }

    def "should return an error when Physician Specialization name is too short or too long"(String invalidSpecializationName) {
        given:
            def specializationDTO = PhysicianFixtures.createSpecializationDto(invalidSpecializationName)

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(specializationDTO)))

        then:
            result.andExpect(status().isBadRequest())
        where:
            invalidSpecializationName                                      | _
            'Ne'                                                           | _
            'internationalizationinternationalizationinternationalization' | _
    }

}
