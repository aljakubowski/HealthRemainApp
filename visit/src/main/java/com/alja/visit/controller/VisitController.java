package com.alja.visit.controller;

import com.alja.visit.controller_resource.VisitResource;
import com.alja.visit.dto.VisitNewDTO;
import com.alja.visit.dto.VisitResponseDTO;
import com.alja.visit.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class VisitController implements VisitResource {

    private final VisitService visitService;

    @Override
    public VisitResponseDTO addNewVisit(VisitNewDTO visitNewDTO) {
        return visitService.addNewVisit(visitNewDTO);
    }

    @Override
    public List<VisitResponseDTO> getAllVisits() {
        return visitService.getAllVisit();
    }
}
