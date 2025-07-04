package com.casacafemonteria.cart.presentation.dto;

import com.casacafemonteria.itemCart.presentation.dto.ItemCartDto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CartDto(
        Long id,
        UUID customerUuid, // extraer de customerEntity.getUuid();
        List<ItemCartDto> items
) {}
