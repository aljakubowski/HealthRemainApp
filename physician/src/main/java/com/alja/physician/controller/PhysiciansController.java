package com.alja.physician.controller;

import com.alja.physician.controller_resources.PhysiciansResource;
import com.alja.physician.model.dto.NewPhysicianDTO;
import com.alja.physician.model.dto.PhysicianResponseDTO;
import com.alja.physician.service.PhysicianService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class PhysiciansController implements PhysiciansResource {


    private final PhysicianService physicianService;


    @Override
    public PhysicianResponseDTO getPhysicianById(String doctorId) {
        return physicianService.getPhysicianById(doctorId);
    }

    @Override
    public List<PhysicianResponseDTO> getAllPhysicians() {
        return physicianService.getAllPhysicians();
    }


    @Override
    public void registerNewPhysician(NewPhysicianDTO newPhysicianDTO) {
        physicianService.registerNewPhysician(newPhysicianDTO);
    }


//    @PostMapping
//    public void registerNewDoctor(@RequestBody DoctorEntity newDoctorDTO){
////    public void registerNewDoctor(@RequestBody NewDoctorDTO newDoctorDTO){
//        log.info("new doctor registration {}", newDoctorDTO);
//            adminPanelDoctorsService.registerNewDoctor(newDoctorDTO);
//    }

}
