package com.alja.adminpanel.service;

import com.alja.physician.dto.NewPhysicianDTO;
import com.netflix.discovery.converters.Auto;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alja.physician.client.PhysicianClient;
import com.alja.physician.dto.PhysicianResponseDTO;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminPanelService {

    @Autowired
    private final PhysicianClient physicianClient;

    public List<PhysicianResponseDTO> getAllPhysicians() {
        return physicianClient.getAllPhysicians();
    }

    public void registerNewPhysician(NewPhysicianDTO newPhysicianDTO){
        physicianClient.registerNewPhysician(newPhysicianDTO);
    }

//    private List<PhysicianResponseDTO> convertDTO(List<com.alja.physician.model.dto.PhysicianResponseDTO> physicianResponseDTO){
//        List<PhysicianResponseDTO> physicianResponseDTOS = new ArrayList<>();
//        for (PhysicianResponseDTO physician: physicianResponseDTO) {
//            physicianResponseDTOS.add(new PhysicianResponseDTO(physician.firstName(), physician.lastName(), physician))
//        }
//    }
}
