package com.alja.physician.service;

import com.alja.visit.client.VisitClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientsService {

    private final VisitClient visitClient;
    private final boolean HAS_NO_VISITS = false;
    private final boolean HAS_VISITS = true;

    public boolean hasVisitsAppointed(String physicianId) {
        return visitClient.checkVisits(physicianId).isHasVisits()
                ? HAS_VISITS :
                HAS_NO_VISITS;
    }
}
