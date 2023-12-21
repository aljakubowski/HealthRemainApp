package com.alja.common.annotation;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NotNull(message = "Last name should not be empty")
@Size(min = 2, max = 50, message = "Last name should have 3 to 50 characters")
public @interface LastName {
}
