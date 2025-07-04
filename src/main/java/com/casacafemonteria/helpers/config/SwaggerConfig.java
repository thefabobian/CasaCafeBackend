package com.casacafemonteria.helpers.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Sistema de Gestión de Caja y Ventas",
                description = "API REST Backend para la Implementación de sistemas de caja y gestión de ventas para Casacafé Montería.",
                termsOfService = "contacto@casacafe.com",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipo #1 🧨",
                        url = "https://www.casacafe.com",
                        email = "soporte@casacafe.com"
                ),
                license = @License(
                        name = "Licencia Comercial",
                        url = "https://www.casacafe.com/licencia"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor de Desarrollo",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de Producción",
                        url = "http://casacafe.example.com"
                )
        }
)
public class SwaggerConfig {
}
