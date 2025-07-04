package com.casacafemonteria.detailsBill.presentation.controller;

import com.casacafemonteria.constants.EndPointsConstants;
import com.casacafemonteria.detailsBill.presentation.dto.DetailBillDto;
import com.casacafemonteria.detailsBill.presentation.payload.DetailBillPayload;
import com.casacafemonteria.detailsBill.service.interfaces.IDetailBillService;
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
@RequestMapping(EndPointsConstants.ENDPOINT_DETAIL_BILL)
@RequiredArgsConstructor
@Tag(name = "BillDetails", description = "Operaciones relacionadas con los detalles de la factura")
public class DetailBillController {

    private final IDetailBillService billDetailService;

    @Operation(summary = "Agregar un detalle a la factura")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Detalle agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody DetailBillPayload payload) {
        billDetailService.create(payload);
        return ResponseEntity.created(URI.create(EndPointsConstants.ENDPOINT_DETAIL_BILL)).build();
    }

    @Operation(summary = "Eliminar un detalle de factura por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        billDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los detalles de una factura")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalles encontrados")
    })
    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<DetailBillDto>> findAllByBill(@PathVariable Long billId) {
        return ResponseEntity.ok(billDetailService.getByBillId(billId));
    }

    @Operation(summary = "Buscar un detalle por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetailBillDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(billDetailService.findById(id));
    }
}
