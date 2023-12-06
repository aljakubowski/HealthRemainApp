package com.alja.physician.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
    @Size(min = 3, max = 100)
    private String street;
    @Size(min = 1, max = 4)
    private String houseNumber;
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
    private String postCode;
    @Size(min = 3, max = 100)
    private String city;
    @Size(min = 3, max = 100)
    private String country;
}