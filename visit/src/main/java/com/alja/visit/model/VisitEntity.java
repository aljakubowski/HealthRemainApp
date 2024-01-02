package com.alja.visit.model;

import com.alja.common.enums.VisitStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
public class VisitEntity {
    @Id
    private String id;
    private String visitId;
    private String physicianId;
    private String physicianSpecialization;
    private String patientId;
    private VisitStatus visitStatus;
    private LocalDateTime visitStartDate;
    private LocalDateTime visitEndDate;
    private List<String> physicianRecommendations;
}
