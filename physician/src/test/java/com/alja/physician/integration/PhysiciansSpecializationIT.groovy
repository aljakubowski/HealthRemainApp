package com.alja.physician.integration

import com.alja.physician.config.AppIntegrationTest

import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.physician.model.mapper.PhysicianSpecializationMapper
import com.alja.physician.model.repository.PhysicianSpecializationRepository
import com.alja.physician.service.PhysiciansSpecializationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import static com.alja.physician.controller_resources.PhysiciansSpecializationResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class PhysiciansSpecializationIT extends AppIntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private PhysiciansSpecializationService physiciansSpecializationService

    @Autowired
    private PhysicianSpecializationRepository physicianSpecializationRepository

    @Autowired
    private PhysicianSpecializationMapper physicianSpecializationMapper

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

}
