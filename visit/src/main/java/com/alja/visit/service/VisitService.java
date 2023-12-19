package com.alja.visit.service;

import com.alja.physician.client.PhysicianClient;
import com.alja.visit.dto.VisitNewDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.model.mapper.VisitMapper;
import com.alja.visit.model.repository.VisitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alja.visit.VisitLogs.ADD_VISIT;

@Slf4j
@AllArgsConstructor
@Service
public class VisitService {

    private final PhysicianClient physicianClient;
    private final VisitRepository visitRepository;
//    private final VisitMapper visitMapper;
    private final LogService logService;

    public VisitResponseDTO addNewVisit(VisitNewDTO visitNewDTO) {
        logService.logOperation(ADD_VISIT.logMessage, visitNewDTO.getVisitDate().toString());
        //todo validation
        //validate if physician exists
        //validate if physician is available // later // visitDate & durationMinutes

        //todo mapping values
        //todo saving
//        visitRepository.save()
        //todo response
        //todo unit test
        return null;
    }


    public List<VisitResponseDTO> getAllVisit() {
        //todo logging
        //todo validation
        //todo mapping values
        //todo response
        //todo unit test
        return null;
    }
}
