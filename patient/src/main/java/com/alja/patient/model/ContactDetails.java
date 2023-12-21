package com.alja.patient.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
public class ContactDetails {
    @Indexed(unique = true)
    private String phoneNumber;
    @Indexed(unique = true)
    private String email;
}
