package com.alja.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailsDTO {
    @Schema(example = "123456789", required = true, description = "phone number")
    @NotNull(message = "Phone number should not be empty")
    @Pattern(regexp = "^\\d{9}$", message = "required format: 9 digit number")
    private String phoneNumber;
    @Schema(example = "myemail@mail.com", required = true, description = "email address")
    @NotNull(message = "Email should not be empty")
    @Email(message = "email should have the correct format")
    private String email;
}
