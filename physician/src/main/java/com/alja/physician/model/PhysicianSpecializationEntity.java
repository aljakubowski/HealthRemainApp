package com.alja.physician.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class PhysicianSpecializationEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String specializationName;

    public void updateSpecializationName(String newSpecializationName){
        this.specializationName = newSpecializationName;
    }
}
