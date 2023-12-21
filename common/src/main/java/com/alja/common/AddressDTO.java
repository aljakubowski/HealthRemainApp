package com.alja.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    @NotNull(message = "street should not be empty")
    @Size(min = 3, max = 50, message = "street name should have 3 to 50 characters")
    private String street;
    @NotNull(message = "house number should not be empty")
    @Size(min = 1, max = 4, message = "house number should have 1 to 4 characters")
    private String houseNumber;
    @NotNull(message = "post code should not be empty")
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "required format: XX-XXX")
    private String postCode;
    @NotNull(message = "city should not be empty")
    @Size(min = 3, max = 50, message = "city should have 3 to 50 characters")
    private String city;
    @NotNull(message = "country should not be empty")
    @Size(min = 3, max = 50, message = "country should have 3 to 50 characters")
    private String country;
}