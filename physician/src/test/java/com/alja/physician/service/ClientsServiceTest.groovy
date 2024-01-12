package com.alja.physician.service

import com.alja.physician.fixtures.PhysicianFixtures
import com.alja.visit.client.VisitClient
import spock.lang.Specification

class ClientsServiceTest extends Specification {

    private ClientsService clientsService
    private VisitClient visitClient = Mock()

    def setup() {
        clientsService = new ClientsService(visitClient)
    }

    def 'should return appropriate value when check visit response'(boolean value) {
        given:
            def visitCheckResponse = PhysicianFixtures.getVisitCheckResponse(value)
            visitClient.checkVisits(_ as String) >> visitCheckResponse
        expect:
            clientsService.hasVisitsAppointed(_ as String) == result
        where:
            value || result
            true  || true
            false || false
    }
}