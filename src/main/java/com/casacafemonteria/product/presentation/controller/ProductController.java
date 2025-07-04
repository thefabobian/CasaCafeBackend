package com.casacafemonteria.product.presentation.controller;

import com.casacafemonteria.cloudinary.CloudinaryService;
import com.casacafemonteria.constants.EndPointsConstants;
import com.casacafemonteria.product.presentation.dto.ProductDto;
import com.casacafemonteria.product.presentation.payload.ProductPayload;
import com.casacafemonteria.product.service.interfaces.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(EndPointsConstants.ENDPOINT_PRODUCT)
@RequiredArgsConstructor
@Tag(name = "Products", description = "Operaciones relacionadas con los productos")
public class ProductController {

    private final IProductService productService;
    private final CloudinaryService cloudinaryService;

    @Operation(summary = "Crear un nuevo producto con imagen")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(
            @RequestPart("payload") String payload,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductPayload productPayload = mapper.readValue(payload, ProductPayload.class);

        String imageUrl = cloudinaryService.uploadImage(image);
        productPayload.setImageUrl(imageUrl);
        productService.save(productPayload);

        return ResponseEntity.created(URI.create(EndPointsConstants.ENDPOINT_PRODUCT)).build();
    }

    @Operation(summary = "Actualizar un producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestPart("payload") String payloadJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        // Convertir el JSON a ProductPayload
        ObjectMapper mapper = new ObjectMapper();
        ProductPayload payload = mapper.readValue(payloadJson, ProductPayload.class);

        // Si se incluye nueva imagen, subirla y actualizar la URL
        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(image);
            payload.setImageUrl(imageUrl);
        }

        productService.update(payload, id);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los productos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados")
    })
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @Operation(summary = "Buscar producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Productos encontrados")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.searchByName(name, pageable));
    }
}
