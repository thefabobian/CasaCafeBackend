package com.casacafemonteria.cart.presentation.controller;

import com.casacafemonteria.cart.presentation.dto.CartDto;
import com.casacafemonteria.cart.presentation.payload.CartPayload;
import com.casacafemonteria.cart.service.interfaces.ICartService;
import com.casacafemonteria.constants.EndPointsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(EndPointsConstants.ENDPOINT_CART)
@RequiredArgsConstructor
@Tag(name = "Carts", description = "Operaciones relacionadas con los carritos")
public class CartController {
    private final ICartService cartService;

    @Operation(summary = "Crear carrito para un cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    })
    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody CartPayload payload) {
        cartService.create(payload);
        return ResponseEntity.created(URI.create(EndPointsConstants.ENDPOINT_CART)).build();
    }

    @Operation(summary = "Obtener carrito por UUID del cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito o cliente no encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<CartDto> getByCustomerUuid(@PathVariable UUID uuid) {
        CartDto result = cartService.getByCustomerUuid(uuid);
        return ResponseEntity.ok(result);
    }
}
