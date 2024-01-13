package com.alja.visit.controller

import com.alja.common.enums.VisitStatus
import com.alja.patient.client.PatientClient
import com.alja.patient.dto.PatientResponseDTO
import com.alja.physician.client.PhysicianClient
import com.alja.visit.config.AppIntegrationTest
import com.alja.visit.fixtures.VisitFixtures
import com.alja.visit.model.mapper.VisitMapper
import com.alja.visit.model.repository.VisitRepository
import com.alja.visit.service.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore

import java.time.LocalDateTime

import static com.alja.visit.controller_resource.VisitPatientResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class VisitPatientControllerTest extends AppIntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private VisitAdminService visitAdminService

    @Autowired
    private VisitValidationService visitValidationService

    @Autowired
    private VisitUpdateService visitUpdateService

    @Autowired
    private VisitResponseService visitResponseService

    @Autowired
    private VisitMapper visitMapper

    @Autowired
    private VisitSorter visitSorter

    @Autowired
    private LogService logService

    @Autowired
    private VisitRepository visitRepository

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ClientsService clientsService

    @MockBean
    private PhysicianClient physicianClient

    @MockBean
    private PatientClient patientClient

    void cleanup() {
        visitRepository.deleteAll()
    }

    def visitDate = LocalDateTime.of(2024, 2, 4, 12, 0)
    def physicianId = 'physicianId'
    def patientId = 'patientId'
    def firstName = 'Jan'
    def lastName = 'Good'
    def physiciansSpecialization = 'radiologist'
    def visitId = 'visitId'
    def visitStartDate = LocalDateTime.of(2024, 1, 1, 12, 0, 0)
    def visitEndDate = LocalDateTime.of(2024, 1, 1, 12, 30, 0)


    @Ignore
    def 'should get all patients visit'() {
        given:
            def visitStatus = VisitStatus.RESERVED

            def visitEntity
                    = VisitFixtures.createVisitEntityWithIdAndPatientAndStatus(visitId, patientId, visitStatus)
            def visitEntity1
                    = VisitFixtures.createVisitEntityWithIdAndPatientAndStatus(visitId, patientId, visitStatus)

            visitRepository.save(visitEntity)
            visitRepository.save(visitEntity1)

            def PATIENT_ID_PATH = "/" + patientId

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PATIENT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.[0].visitId').isNotEmpty())
            result.andExpect(jsonPath('$.[0].visitStatus').value(visitStatus.name()))
    }

    def 'should get available visits with filter'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitId1 = 'visitId1'
            def visitId2 = 'visitId2'

            def visitEntity1 = VisitFixtures.createVisitEntityWithIdAndStatus(visitId1, visitStatus)
            def visitEntity2 = VisitFixtures.createVisitEntityWithIdAndStatus(visitId2, visitStatus)
            visitRepository.save(visitEntity1)
            visitRepository.save(visitEntity2)

            def visitFilter = VisitFixtures.createVisitCommonFilterWithStatusAvailable()

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(visitFilter)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.[0].visitId').isNotEmpty())
            result.andExpect(jsonPath('$.[1].visitId').isNotEmpty())
            result.andExpect(jsonPath('$.[0].visitId').value(visitId1))
            result.andExpect(jsonPath('$.[1].visitId').value(visitId2))
            result.andExpect(jsonPath('$.[0].visitStatus').value(visitStatus.name()))
            result.andExpect(jsonPath('$.[1].visitStatus').value(visitStatus.name()))
    }

    @Ignore
    def 'should get patients visit by id'() {
        given:
            def visitStatus = VisitStatus.RESERVED

            def visitEntity
                    = VisitFixtures.createVisitEntityWithIdAndPatientAndStatus(visitId, patientId, visitStatus)
            visitRepository.save(visitEntity)

            def PATIENT_ID_PATH = "/" + patientId
            def VISIT_ID_PATH = "/" + visitId

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + PATIENT_ID_PATH + VISIT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.visitId').isNotEmpty())
            result.andExpect(jsonPath('$.visitStatus').value(visitStatus.name()))
    }

    @Ignore
    def 'should make visit appointment'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitStatus1 = VisitStatus.RESERVED

            def visitEntity
                    = VisitFixtures.createVisitEntityWithIdAndPatientAndStatus(visitId, patientId, visitStatus)
            visitRepository.save(visitEntity)

            def PATIENT_ID_PATH = "/" + patientId
            def VISIT_ID_PATH = "/" + visitId

        when:
            def result = this.mockMvc.perform(post(RESOURCE_PATH + PATIENT_ID_PATH + VISIT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.visitId').isNotEmpty())
            result.andExpect(jsonPath('$.visitStatus').value(visitStatus1.name()))
    }

    @Ignore
    def 'should cancel visit appointment'() {
        given:
            def visitStatus = VisitStatus.RESERVED
            def visitStatus1 = VisitStatus.AVAILABLE

            def visitEntity
                    = VisitFixtures.createVisitEntityWithIdAndPatientAndStatus(visitId, patientId, visitStatus)
            visitRepository.save(visitEntity)

            def PATIENT_ID_PATH = "/" + patientId
            def VISIT_ID_PATH = "/" + visitId

//        clientsService.getPatientResponseDTO(patientId) >> PatientResponseDTO.builder().patientId(patientId).build()
            patientClient.getPatientById(patientId, 'DETAILS') >> PatientResponseDTO.builder().patientId(patientId).build()

        when:
            def result = this.mockMvc.perform(put(RESOURCE_PATH + PATIENT_ID_PATH + VISIT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.visitId').isNotEmpty())
            result.andExpect(jsonPath('$.visitStatus').value(visitStatus1.name()))
    }
}
