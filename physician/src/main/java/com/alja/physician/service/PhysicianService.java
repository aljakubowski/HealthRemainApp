package com.alja.physician.service;

import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import com.alja.physician.model.Address;
import com.alja.physician.model.PhysicianEntity;
//import com.alja.physician.model.mapper.PhysicianMapper;
import com.alja.physician.model.repository.PhysicianRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class PhysicianService {

    private final PhysicianRepository physicianRepository;
//    private final PhysicianMapper physicianMapper;

    public void registerNewPhysician(NewPhysicianDTO newPhysicianDTO) {
        //// TODO: 29/11/2023 validation
        // mapperservice, mapstruct?
        // logs
        log.info("DO ZROBIENIA");


        // phoneNum, registrationDate, UUID employeeID

        PhysicianEntity physicianEntity = PhysicianEntity.builder()
                .firstName(newPhysicianDTO.getFirstName())
                .lastName(newPhysicianDTO.getLastName())
                .physicianSpecialization(newPhysicianDTO.getPhysicianSpecialization())
                .email(newPhysicianDTO.getEmail())
                .address(Address.builder()
                        .city(newPhysicianDTO.getAddress().getCity())
                        .street(newPhysicianDTO.getAddress().getStreet())
                        .postCode(newPhysicianDTO.getAddress().getPostCode())
                        .build())
                .build();
//        PhysicianEntity physicianEntity = physicianMapper.physicianEntityDtoToEntity(newPhysicianDTO);
        physicianRepository.save(physicianEntity);
    }

    public PhysicianResponseDTO getPhysicianById(String id) {
        log.info("DO ZROBIENIA");
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
        PhysicianResponseDTO.builder().build();
        return PhysicianResponseDTO.builder()
                .id(physicianEntity.getId())
                .firstName(physicianEntity.getFirstName())
                .lastName(physicianEntity.getLastName())
                .physiciansSpecialization(physicianEntity.getPhysicianSpecialization())
                .build();
    }
}
