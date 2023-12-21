package com.alja.patient.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String street;
    private String houseNumber;
    private String postCode;
    private String city;
    private String country;
}
