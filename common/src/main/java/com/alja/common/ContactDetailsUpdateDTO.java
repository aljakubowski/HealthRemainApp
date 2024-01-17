package com.alja.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDetailsUpdateDTO {

    @Schema(example = "123456789", description = "phone number")
    @Nullable
    @Pattern(regexp = "^\\d{9}$", message = "required format: 9 digit number")
    private String phoneNumber;
    @Schema(example = "myemail@mail.com", description = "email address")
    @Nullable
    @Email
    private String email;
}
