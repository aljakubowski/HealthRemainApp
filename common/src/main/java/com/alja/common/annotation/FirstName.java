package com.alja.common.annotation;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NotNull(message = "First name should not be empty")
@Size(min = 2, max = 50, message = "First name should have 2 to 50 characters")
public @interface FirstName {
}
