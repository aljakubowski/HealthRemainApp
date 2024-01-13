package com.alja.visit.controller

import com.alja.common.enums.VisitStatus
import com.alja.patient.client.PatientClient
import com.alja.physician.client.PhysicianClient
import com.alja.visit.config.AppIntegrationTest
import com.alja.visit.dto.VisitNewDTO
import com.alja.visit.fixtures.VisitFixtures
import com.alja.visit.model.mapper.VisitMapper
import com.alja.visit.model.repository.VisitRepository
import com.alja.visit.service.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore

import java.time.LocalDateTime

import static com.alja.visit.controller_resource.VisitAdminResource.RESOURCE_PATH
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class VisitAdminControllerTest extends AppIntegrationTest {

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
    def 'should add a new visit'() {
        given:

            def NO_DETAILS = false
            def physicianResponse
                    = VisitFixtures.createPhysicianResponseDtoWithCustomFields(
                    physicianId, firstName, lastName, physiciansSpecialization)


//            physicianClient.getPhysicianById(physicianId, NO_DETAILS) >> physicianResponse
//            physicianClient.getPhysicianById(physicianId, NO_DETAILS) >> ResponseEntity.status(HttpStatus.OK).body(physicianResponse)
//            clientsService.getPhysicianResponseDTO(physicianId) >> physicianResponse
//            clientsService.getPhysicianResponseDTO(physicianId) >> ResponseEntity.status(HttpStatus.OK).body(physicianResponse)


            VisitNewDTO visitNewDTO = VisitFixtures.createVisitNewDTO(physicianId, visitDate)
        when:
            physicianClient.getPhysicianById(physicianId, NO_DETAILS) >> ResponseEntity.status(HttpStatus.OK).body(physicianResponse)


            def result = this.mockMvc.perform(post(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(visitNewDTO)))

        then:
            result.andExpect(status().isCreated())
//            result.andExpect(jsonPath('$.visitId').isNotEmpty())
//            result.andExpect(jsonPath('$.physicianResponseDTO.physicianId').value(physicianId))
//            result.andExpect(jsonPath('$.physicianResponseDTO.firstName').value(firstName))
//            result.andExpect(jsonPath('$.physicianResponseDTO.lastName').value(lastName))
//            result.andExpect(jsonPath('$.physicianResponseDTO.physiciansSpecialization').value(physiciansSpecialization))
    }


    def 'should get visit by id'() {
        given:
            def visitStatus = VisitStatus.RESERVED

            def visitEntity
                    = VisitFixtures.createVisitEntityWithAllFields(visitId, physicianId, physiciansSpecialization,
                    patientId, visitStatus, visitStartDate, visitEndDate)
            visitRepository.save(visitEntity)

            def VISIT_ID_PATH = "/" + visitId

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH + VISIT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.visitId').isNotEmpty())
            result.andExpect(jsonPath('$.visitStartDate').isNotEmpty())
            result.andExpect(jsonPath('$.visitEndDate').isNotEmpty())
            result.andExpect(jsonPath('$.visitStatus').value(visitStatus.name()))
    }

    def 'should get visit with filter'() {
        given:
            def visitStatus1 = VisitStatus.RESERVED
            def visitStatus2 = VisitStatus.AVAILABLE
            def dateFrom = LocalDateTime.of(2024, 1, 1, 12, 0, 0)
            def dateTo = LocalDateTime.of(2024, 3, 1, 12, 0, 0)

            def date1 = LocalDateTime.of(2024, 1, 11, 12, 0, 0)
            def date2 = LocalDateTime.of(2024, 4, 11, 12, 0, 0)

            def visitId1 = 'visitId1'
            def visitId2 = 'visitId2'

            def visitEntity1
                    = VisitFixtures.createVisitEntityWithAllFields(visitId1, physicianId, physiciansSpecialization,
                    patientId, visitStatus1, date1, date1.plusMinutes(30))
            def visitEntity2
                    = VisitFixtures.createVisitEntityWithAllFields(visitId2, physicianId, physiciansSpecialization,
                    patientId, visitStatus2, date2, date2.plusMinutes(30))
            visitRepository.save(visitEntity1)
            visitRepository.save(visitEntity2)

            def visitFilter
                    = VisitFixtures.createVisitFilterWithStatusAndDates(visitStatus1.name(), dateFrom, dateTo)

        when:
            def result = this.mockMvc.perform(get(RESOURCE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(visitFilter)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.[0].visitId').isNotEmpty())
            result.andExpect(jsonPath('$.[0].visitId').value(visitId1))
            result.andExpect(jsonPath('$.[0].visitStatus').value(visitStatus1.name()))
    }

    def 'should update visit'() {
        given:
            def visitStatus1 = VisitStatus.RESERVED
            def visitStatusUpdated = VisitStatus.AVAILABLE

            def date1 = LocalDateTime.of(2124, 1, 11, 12, 0, 0)
            def emptyPatientId = ''

            def visitEntity
                    = VisitFixtures.createVisitEntityWithAllFields(visitId, physicianId, physiciansSpecialization,
                    emptyPatientId, visitStatus1, date1, date1.plusMinutes(30))

            visitRepository.save(visitEntity)

            def visitUpdate
                    = VisitFixtures.createVisitUpdateDtoWithStatus(visitStatusUpdated.name())

            def VISIT_ID_PATH = "/" + visitId
        when:
            def result = this.mockMvc.perform(put(RESOURCE_PATH + VISIT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(visitUpdate)))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.visitId').isNotEmpty())
            result.andExpect(jsonPath('$.visitStatus').value(visitStatusUpdated.name()))
    }

    def 'should delete visit'() {
        given:
            def visitStatus = VisitStatus.AVAILABLE
            def visitEntity
                    = VisitFixtures.createVisitEntityWithIdAndStatus(visitId, visitStatus)

            visitRepository.save(visitEntity)

            def VISIT_ID_PATH = "/" + visitId
        when:
            def result = this.mockMvc.perform(delete(RESOURCE_PATH + VISIT_ID_PATH)
                    .contentType(MediaType.APPLICATION_JSON))

        then:
            result.andExpect(status().isOk())
            result.andExpect(jsonPath('$.visitId').isNotEmpty())
            result.andExpect(jsonPath('$.visitStatus').value(visitStatus.name()))
    }

}
