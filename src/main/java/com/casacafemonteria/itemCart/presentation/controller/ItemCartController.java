package com.casacafemonteria.itemCart.presentation.controller;

import com.casacafemonteria.constants.EndPointsConstants;
import com.casacafemonteria.itemCart.presentation.dto.ItemCartDto;
import com.casacafemonteria.itemCart.presentation.payload.ItemCartPayload;
import com.casacafemonteria.itemCart.service.interfaces.IItemCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(EndPointsConstants.ENDPOINT_ITEM_CART)
@RequiredArgsConstructor
@Tag(name = "ItemCart", description = "Operaciones relacionadas con los ítems del carrito")
public class ItemCartController {

    private final IItemCartService itemCartService;

    @Operation(summary = "Agregar un producto al carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ítem agregado al carrito exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ItemCartPayload payload) {
        itemCartService.addItem(payload);
        return ResponseEntity.created(URI.create(EndPointsConstants.ENDPOINT_ITEM_CART)).build();
    }

    @Operation(summary = "Actualizar un ítem del carrito por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítem actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ItemCartPayload payload) {
        itemCartService.updateItem(payload, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar un ítem del carrito por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ítem eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemCartService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los ítems de un carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítems encontrados")
    })
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<ItemCartDto>> findAllByCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(itemCartService.getItemsByCartId(cartId));
    }

    @Operation(summary = "Buscar un ítem del carrito por ID y carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítem encontrado"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @GetMapping("/cart/{cartId}/item/{itemId}")
    public ResponseEntity<ItemCartDto> findByIdAndCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        return ResponseEntity.ok(itemCartService.findByIdAndCart(itemId, cartId));
    }

}