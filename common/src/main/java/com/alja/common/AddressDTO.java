package com.alja.common;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "Golden", required = true, description = "street name")
    @NotNull(message = "street should not be empty")
    @Size(min = 3, max = 50, message = "street name should have 3 to 50 characters")
    private String street;
    @Schema(example = "71", required = true, description = "house number")
    @NotNull(message = "house number should not be empty")
    @Size(min = 1, max = 4, message = "house number should have 1 to 4 characters")
    private String houseNumber;
    @Schema(example = "01-292", required = true, description = "post code")
    @NotNull(message = "post code should not be empty")
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "required format: XX-XXX")
    private String postCode;
    @Schema(example = "Warsaw", required = true, description = "city name")
    @NotNull(message = "city should not be empty")
    @Size(min = 3, max = 50, message = "city should have 3 to 50 characters")
    private String city;
    @Schema(example = "Poland", required = true, description = "country name")
    @NotNull(message = "country should not be empty")
    @Size(min = 3, max = 50, message = "country should have 3 to 50 characters")
    private String country;
}