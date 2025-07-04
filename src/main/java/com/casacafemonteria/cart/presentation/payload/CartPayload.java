package com.casacafemonteria.cart.presentation.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartPayload {
    @NotNull(message = "El UUID del cliente es obligatorio")
    private UUID customerUuid;
}
