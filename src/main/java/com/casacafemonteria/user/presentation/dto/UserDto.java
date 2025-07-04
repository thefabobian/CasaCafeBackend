package com.casacafemonteria.user.presentation.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String fullName,
        String email
) {
}
