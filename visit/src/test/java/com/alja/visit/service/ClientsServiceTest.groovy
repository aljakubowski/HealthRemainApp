package com.alja.visit.service

import com.alja.patient.client.PatientClient
import com.alja.physician.client.PhysicianClient
import com.alja.visit.fixtures.VisitFixtures
import spock.lang.Specification

class ClientsServiceTest extends Specification {

    private ClientsService clientsService
    private PhysicianClient physicianClient = Mock()
    private PatientClient patientClient = Mock()

    def setup() {
        clientsService = new ClientsService(physicianClient, patientClient)
    }

    def 'should get physician response'() {
        given:
            def physicianId = "physicianId"
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def physicianSpecialization = 'radiologist'
            def physicianResponse
                    = VisitFixtures.createPhysicianResponseDtoWithCustomFields(physicianId, firstName, lastName, physicianSpecialization)

            physicianClient.getPhysicianById(physicianId, false) >> physicianResponse

        when:
            def result = clientsService.getPhysicianResponseDTO(physicianId)

        then:
            result != null
            result.physicianId == physicianId
            result.firstName == firstName
            result.lastName == lastName
            result.physiciansSpecialization == physicianSpecialization
    }

    def 'should get patient response if patient id is not null'() {
        given:
            def patientId = "patientId"
            def firstName = 'Jan'
            def lastName = 'Dobry'
            def patientResponse = VisitFixtures.createPatientResponseDTO(patientId, firstName, lastName)

            patientClient.getPatientById(patientId, _ as String) >> patientResponse

        when:
            def result = clientsService.getPatientResponseDTO(patientId)

        then:
            result != null
            result.patientId == patientId
            result.firstName == firstName
            result.lastName == lastName
    }


    def 'should return null for patient response if patient id is blank'(String patientId) {
        expect:
            clientsService.getPatientResponseDTO(patientId) == result

        where:
            patientId | result
            ''        | null
            ' '       | null
            null      | null
    }

    def 'should get String list of available specializations'() {
        given:
            def specialization1 = "specialization1"
            def specialization2 = "specialization2"

            def physicianSpecializationDto1
                    = VisitFixtures.getPhysicianSpecializationDtoWithName(specialization1)
            def physicianSpecializationDto2
                    = VisitFixtures.getPhysicianSpecializationDtoWithName(specialization2)

            physicianClient.getAllSpecializations() >> [physicianSpecializationDto1, physicianSpecializationDto2]

        when:
            def result = clientsService.getAvailableSpecializations()

        then:
            result != null
        result.size() == 2
        result.get(0) == specialization1
        result.get(1) == specialization2
    }

}
