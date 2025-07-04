package com.casacafemonteria.security.auth.controller.payload;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}
