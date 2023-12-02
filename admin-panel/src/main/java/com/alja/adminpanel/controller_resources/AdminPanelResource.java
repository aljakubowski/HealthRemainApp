package com.alja.adminpanel.controller_resources;

import org.springframework.web.bind.annotation.*;
import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianResponseDTO;

import java.util.List;

import static com.alja.adminpanel.controller_resources.AdminPanelResource.RESOURCE_PATH;

@RequestMapping(RESOURCE_PATH)
public interface AdminPanelResource {

    String RESOURCE_PATH = "/api/v1/admin-panel";
    String PHYSICIAN_ID_PATH = "/{physicianId}";

    @GetMapping(PHYSICIAN_ID_PATH)
    PhysicianResponseDTO getPhysicianById(@PathVariable String physicianId);

    @GetMapping
    List<PhysicianResponseDTO> getAllPhysicians();

    @PostMapping
    void registerNewPhysician(@RequestBody NewPhysicianDTO newPhysicianDTO);


}
