package com.alja.physician.client;

import com.alja.physician.dto.NewPhysicianDTO;
import com.alja.physician.dto.PhysicianResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "com/alja/physician",
//        url = "${common.physician.url}")
@FeignClient("physician")
public interface PhysicianClient {
    //todo path to variable?

    @GetMapping(path = "api/v1/physician")
    List<PhysicianResponseDTO> getAllPhysicians();

    @PostMapping(path = "api/v1/physician")
    void registerNewPhysician(@RequestBody NewPhysicianDTO newPhysicianDTO);

}
