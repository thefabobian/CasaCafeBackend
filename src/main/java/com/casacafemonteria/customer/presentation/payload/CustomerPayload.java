package com.casacafemonteria.customer.presentation.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPayload {
    private UUID uuid; // solo para actualizar
    @NotBlank(message = "El campo 'username' es obligatorio")
    private String username;
    @Email(message = "El correo electrónico no es válido")
    private String email;
    @NotBlank(message = "El campo 'password' es obligatorio")
    private String password;
    @NotBlank(message = "El campo 'dni' es obligatorio")
    private String dni;
    @NotBlank(message = "El campo 'telefono' es obligatorio")
    private String phone;
    @NotBlank(message = "El campo 'dirección' es obligatorio")
    private String address;
    @NotNull(message = "El campo 'fecha de nacimiento' es obligatorio")
    private Date birth;
}