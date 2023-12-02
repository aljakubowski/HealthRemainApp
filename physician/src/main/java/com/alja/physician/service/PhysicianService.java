package com.alja.physician.service;

import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.model.Address;
import com.alja.physician.model.PhysicianEntity;
import com.alja.physician.model.PhysicianRepository;
import com.alja.enums.PhysicianSpecialization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PhysicianService {

    private final PhysicianRepository physicianRepository;

    public void registerNewPhysician(NewPhysicianDTO newPhysicianDTO) {
        //// TODO: 29/11/2023 validation

        PhysicianEntity physicianEntity = PhysicianEntity.builder()
                .firstName(newPhysicianDTO.getFirstName())
                .lastName(newPhysicianDTO.getLastName())
                .email(newPhysicianDTO.getEmail())
                .physicianSpecialization(newPhysicianDTO.getPhysicianSpecialization())
                .address(Address.builder()
                        .city(newPhysicianDTO.getAddress().getCity())
                        .street(newPhysicianDTO.getAddress().getStreet())
                        .postCode(newPhysicianDTO.getAddress().getPostCode())
                        .build())
                .build();
        physicianRepository.save(physicianEntity);
    }

    public PhysicianResponseDTO getPhysicianById(String id) {
        PhysicianEntity physicianEntity = physicianRepository.findById(id).orElse(null);
        // response / full response format
        return getDoctorResponseDTO(physicianEntity);
    }

    public List<PhysicianResponseDTO> getAllPhysicians() {
        List<PhysicianEntity> doctorEntities = physicianRepository.findAll();
        List<PhysicianResponseDTO> physicianResponseDTOS = new ArrayList<>();
        for (PhysicianEntity physicianEntity : doctorEntities) {
            physicianResponseDTOS.add(getDoctorResponseDTO(physicianEntity));
        }
        return physicianResponseDTOS;
    }

    private PhysicianResponseDTO getDoctorResponseDTO(PhysicianEntity physicianEntity){
        return PhysicianResponseDTO.builder()
                .id(physicianEntity.getId())
                .firstName(physicianEntity.getFirstName())
                .lastName(physicianEntity.getLastName())
                .physiciansSpecialization(physicianEntity.getPhysicianSpecialization())
                .build();
    }
}
