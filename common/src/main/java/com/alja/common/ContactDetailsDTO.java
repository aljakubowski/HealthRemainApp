package com.alja.common;

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
    @NotNull(message = "Phone number should not be empty")
    @Pattern(regexp = "^\\d{9}$", message = "required format: 9 digit number")
    private String phoneNumber;
    @NotNull(message = "Email should not be empty")
    @Email(message = "email should have the correct format")
    private String email;
}
