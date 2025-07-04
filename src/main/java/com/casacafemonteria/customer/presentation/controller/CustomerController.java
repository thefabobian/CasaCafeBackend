package com.casacafemonteria.customer.presentation.controller;

import com.casacafemonteria.constants.EndPointsConstants;
import com.casacafemonteria.customer.presentation.dto.CustomerDto;
import com.casacafemonteria.customer.presentation.payload.CustomerPayload;
import com.casacafemonteria.customer.service.interfaces.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(EndPointsConstants.ENDPOINT_CUSTOMER)
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Operaciones relacionadas con los clientes")
public class CustomerController {
    private final ICustomerService customerService;

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody CustomerPayload payload) {
        customerService.save(payload);
        return ResponseEntity.created(URI.create(EndPointsConstants.ENDPOINT_CUSTOMER)).build();
    }

    @Operation(summary = "Actualizar cliente por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @Validated @RequestBody CustomerPayload payload) {
        customerService.update(payload, uuid);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar profesor por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        customerService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar cliente por dni")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrados"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/dni")
    public ResponseEntity<Page<CustomerDto>> findByDni(
            @RequestParam String dni,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userEntity.username") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<CustomerDto> result = customerService.findByDni(dni, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener todos los clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clientes encontrados")
    })
    @GetMapping
    public ResponseEntity<Page<CustomerDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userEntity.username") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<CustomerDto> result = customerService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Buscar cliente por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerDto> findByUuid(@PathVariable UUID uuid) {
        CustomerDto result = customerService.findByUuid(uuid);
        return ResponseEntity.ok(result);
    }
}
