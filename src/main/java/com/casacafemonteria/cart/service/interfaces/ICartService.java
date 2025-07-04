package com.casacafemonteria.cart.service.interfaces;

import com.casacafemonteria.cart.presentation.dto.CartDto;
import com.casacafemonteria.cart.presentation.payload.CartPayload;

import java.util.UUID;

public interface ICartService {
    CartDto create(CartPayload cartPayload);
    CartDto getByCustomerUuid(UUID uuid);
}
