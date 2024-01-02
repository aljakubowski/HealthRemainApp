package com.alja.visit.fixtures;

import com.alja.visit.dto.VisitNewDTO;

import java.time.LocalDateTime;

public class VisitFixtures {

    public static VisitNewDTO createVisitNewDTO(String physicianId, LocalDateTime visitDate) {
        return VisitNewDTO.builder()
                .physicianId(physicianId)
                .visitDate(visitDate)
                .build();
    }

}
