package com.alja.physician.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String city;
    private String street;
    private String postCode;
}
