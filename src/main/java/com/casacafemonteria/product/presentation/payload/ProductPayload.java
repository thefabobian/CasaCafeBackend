package com.casacafemonteria.product.presentation.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPayload {
    private Long id; //Solo para actualizaciones, no para creación
    @NotBlank(message = "El nombre no puede estar vacía")
    private String name;
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;
    @NotNull(message = "El precio no puede ser nulo")
    private Double price;
    @NotNull(message = "El stock no puede ser nulo")
    private Integer stock;
    @NotNull(message = "La imagen no puede ser nula")
    @Column(name = "image_url")
    private String imageUrl; // se llena tras recibir imagen a Cloudinary
}
