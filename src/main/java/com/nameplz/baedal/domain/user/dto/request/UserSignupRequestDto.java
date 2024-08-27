package com.nameplz.baedal.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignupRequestDto(
        @NotBlank
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]*$", message = "Username should only contain lowercase letters and digits.")
        String username,

        @NotBlank
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()]*$", message = "Password must include letters, numbers, and special characters.")
        String password
) {}
