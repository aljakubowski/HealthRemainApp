package com.alja.physician.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    private String city;
    private String street;
    private String postCode;
}
