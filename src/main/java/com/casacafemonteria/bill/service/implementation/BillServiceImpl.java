package com.casacafemonteria.bill.service.implementation;

import com.casacafemonteria.bill.factory.BillFactory;
import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.bill.persistence.repositories.BillRepository;
import com.casacafemonteria.bill.presentation.dto.BillDto;
import com.casacafemonteria.bill.presentation.payload.BillPayload;
import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import com.casacafemonteria.detailsBill.persistence.repositories.DetailBillRepository;
import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.cart.persistence.repositories.CartRepository;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.customer.persistence.repositories.CustomerRepository;
import com.casacafemonteria.helpers.exception.exceptions.ConflictException;
import com.casacafemonteria.helpers.exception.exceptions.ResourceNotFoundException;
import com.casacafemonteria.itemCart.persistence.entities.ItemCartEntity;
import com.casacafemonteria.itemCart.persistence.repositories.ItemCartRepository;
import com.casacafemonteria.product.persistence.entities.ProductEntity;
import com.casacafemonteria.product.persistence.repositories.ProductRepository;
import com.casacafemonteria.pdf.PdfService;
import com.casacafemonteria.bill.service.interfaces.IBillService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements IBillService {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final BillFactory billFactory;
    private final CartRepository cartRepository;
    private final ItemCartRepository itemCartRepository;
    private final ProductRepository productRepository;
    private final DetailBillRepository detailBillRepository;
    private final PdfService pdfService;



    @Override
    @Transactional
    public BillDto create(BillPayload billPayload) {
        CustomerEntity customer = customerRepository.findByUuid(billPayload.getCustomerUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        BillEntity bill = billFactory.billEntity(customer);
        return billFactory.billDto(billRepository.save(bill));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillDto> getByCustomerEntity(UUID customerUuid) {
        CustomerEntity customer = customerRepository.findByUuid(customerUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        return billRepository.findByCustomerEntity(customer)
                .stream()
                .map(billFactory::billDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BillDto getById(Long id) {
        return billRepository.findById(id)
                .map(billFactory::billDto)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));
    }

    @Override
    @Transactional
    public BillDto generarFacturaDesdeCarrito(UUID customerUuid) {
        // Buscar el cliente primero
        CustomerEntity customer = customerRepository.findByUuid(customerUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + customerUuid));

        // Busar el carrito usando el CustomerEntity
        CartEntity cart = cartRepository.findByCustomerEntity(customer)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));

        // Obtener los items del carrito
        List<ItemCartEntity> items = itemCartRepository.findByCartEntity(cart);
        if (items.isEmpty()) {
            throw new ConflictException("El carrito está vacío");
        }

        // Crear y guardar la factura
        BillEntity bill = billRepository.save(BillEntity.builder()
                .customerEntity(customer)
                .date(LocalDate.now())
                .build());

        List<DetailBillEntity> detallesGuardados = new ArrayList<>();

        // Procesar cada item del carrito
        for (ItemCartEntity item : items) {
            ProductEntity product = item.getProductEntity();

            if (product.getStock() < item.getQuantity()) {
                throw new ConflictException("Stock insuficiente para el producto: " + product.getName());
            }

            // Descontar stock y guardar producto actualizado
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            // Crear detalle de factura
            DetailBillEntity detail = DetailBillEntity.builder()
                    .billEntity(bill)
                    .productEntity(product)
                    .quantity(item.getQuantity())
                    .priceUnitary(product.getPrice())
                    .build();

            detallesGuardados.add(detailBillRepository.save(detail));
        }

        // Limpieza del carrito
        itemCartRepository.deleteAll(items);

        // Generar PDF
        byte[] pdfBytes = pdfService.generarFacturaPDF(bill, detallesGuardados);

        // Retornar DTO
        return billFactory.billDto(bill);
    }
}