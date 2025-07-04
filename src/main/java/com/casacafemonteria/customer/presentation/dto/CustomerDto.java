package com.casacafemonteria.customer.presentation.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record CustomerDto(
         String dni,
         String phone,
         String address,
         Date birth,
         String username,
         UUID uuid,
         Date createdAt,
         String email
) {
}
