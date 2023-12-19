package com.alja.visit.controller_resource;

import com.alja.visit.dto.VisitNewDTO;
import com.alja.visit.dto.VisitResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.alja.visit.controller_resource.VisitResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface VisitResource {

    String RESOURCE_PATH = "/api/v1/visit";

    @PostMapping
    VisitResponseDTO addNewVisit(@Valid @RequestBody VisitNewDTO visitNewDTO);

    @GetMapping
    List<VisitResponseDTO> getAllVisits();



//    @PutMapping()
//    PhysicianSpecializationDTO updateSpecialization(@RequestParam("specializationName") String specializationName,
//                              @RequestParam("specializationNewName") String specializationNewName);
//
//    @DeleteMapping()
//    PhysicianSpecializationDTO deleteSpecialization(@RequestParam("specializationName") String specializationName);
}
