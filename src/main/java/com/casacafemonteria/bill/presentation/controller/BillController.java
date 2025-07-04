package com.casacafemonteria.bill.presentation.controller;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.bill.persistence.repositories.BillRepository;
import com.casacafemonteria.bill.presentation.dto.BillDto;
import com.casacafemonteria.bill.presentation.payload.BillPayload;
import com.casacafemonteria.bill.service.interfaces.IBillService;
import com.casacafemonteria.constants.EndPointsConstants;
import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import com.casacafemonteria.detailsBill.persistence.repositories.DetailBillRepository;
import com.casacafemonteria.helpers.exception.exceptions.ResourceNotFoundException;
import com.casacafemonteria.pdf.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EndPointsConstants.ENDPOINT_BILL)
@RequiredArgsConstructor
@Tag(name = "Bills", description = "Operaciones relacionadas con las facturas")
public class BillController {

    private final IBillService billService;
    private final BillRepository billRepository;
    private final DetailBillRepository detailBillRepository;
    private final PdfService pdfService;

    @Operation(summary = "Crear factura para un cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Factura creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody BillPayload payload) {
        billService.create(payload);
        return ResponseEntity.created(URI.create(EndPointsConstants.ENDPOINT_BILL)).build();
    }

    @Operation(summary = "Obtener facturas por UUID del cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Facturas encontradas"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/customer/{uuid}")
    public ResponseEntity<List<BillDto>> getByCustomer(@PathVariable UUID uuid) {
        return ResponseEntity.ok(billService.getByCustomerEntity(uuid));
    }

    @Operation(summary = "Obtener factura por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BillDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getById(id));
    }

    @Operation(summary = "Generar factura desde el carrito del cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Factura generada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Carrito o cliente no encontrado")
    })
    @PostMapping("/generar")
    public ResponseEntity<byte[]> generarFactura(@Validated @RequestBody BillPayload payload) {
        BillDto factura = billService.generarFacturaDesdeCarrito(payload.getCustomerUuid());

        // Buscar la factura real con los detalles
        BillEntity bill = billRepository.findById(factura.id())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));
        List<DetailBillEntity> detalles = detailBillRepository.findByBillEntity(bill);

        byte[] pdfBytes = pdfService.generarFacturaPDF(bill, detalles);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura_" + bill.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}

