# 🛒 Casa Café Montería - Tienda Online (Backend)
---

## Descripción

Este proyecto consiste en una aplicación completa para una tienda virtual llamada **Casa Café Montería**.  
El backend está desarrollado con **Spring Boot** y ofrece una API REST para gestionar productos, clientes, carritos de compras, facturación y autenticación.  

---

## 🚀 Tecnologías Usadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA** con **PostgreSQL (NeonDB)**
- **Spring Security** + JWT para autenticación y autorización
- **Cloudinary** para gestión de imágenes de productos
- **iTextPDF** para generación de facturas en PDF
- **Swagger/OpenAPI** para documentación de la API
- **Maven** como sistema de gestión y compilación

---

## 📁 Estructura del Backend

- `product`: Gestión completa de productos con imágenes almacenadas en Cloudinary.
- `customer`: Registro y administración de clientes, con carrito asignado automáticamente.
- `cart`: Gestión de carritos de compra asociados a clientes.
- `itemCart`: Ítems añadidos a los carritos.
- `bill`: Facturación automática desde el carrito con control de stock.
- `detailsBill`: Detalles de productos incluidos en las facturas.
- `pdf`: Generación de facturas en formato PDF para descarga.
- `security`: Configuración de seguridad con JWT para autenticación y autorización.
- `cloudinary`: Integración con Cloudinary para subida y gestión de imágenes.
- `swagger`: Documentación automática de la API REST.
- `user`: Gestión de dar rol a los usuarios.

---

## ✅ Funcionalidades Principales

- Registro seguro de clientes con validaciones y asignación automática de carrito.
- Gestión completa de productos, incluyendo carga y almacenamiento de imágenes en Cloudinary.
- CRUD completo para productos, clientes y carritos.
- Carrito de compras funcional con agregar, editar cantidad y eliminar ítems.
- Facturación automática con generación de factura PDF y descuento de stock en tiempo real.
- Autenticación segura con JWT y manejo de roles (ADMIN, CUSTOMER).
- Documentación API Swagger para fácil exploración y pruebas.

---

## 🚀 Cómo correr el proyecto

### Backend

1. Configura las variables de entorno (por ejemplo, conexión a BD, claves de Cloudinary).
2. Compila y ejecuta con Maven:
   ```bash
   mvn clean install
   mvn spring-boot:run

3. Accede a Swagger en: http://localhost:8080/swagger-ui.html (configurar según el puerto que utilices)

---

## 👤 Autor

###  **Fabian Espinosa**

🎓 **Estudiante de Ingeniería de Software & Ingeniería de Sistemas**  
🏫 _Universidad de Cartagena_ | _Corporación Universitaria Remington_

---
