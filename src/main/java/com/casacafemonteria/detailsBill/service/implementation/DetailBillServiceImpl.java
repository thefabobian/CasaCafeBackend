package com.casacafemonteria.detailsBill.service.implementation;

import com.casacafemonteria.bill.persistence.entities.BillEntity;
import com.casacafemonteria.bill.persistence.repositories.BillRepository;
import com.casacafemonteria.detailsBill.factory.DetailBillFactory;
import com.casacafemonteria.detailsBill.persistence.entities.DetailBillEntity;
import com.casacafemonteria.detailsBill.persistence.repositories.DetailBillRepository;
import com.casacafemonteria.detailsBill.presentation.dto.DetailBillDto;
import com.casacafemonteria.detailsBill.presentation.payload.DetailBillPayload;
import com.casacafemonteria.detailsBill.service.interfaces.IDetailBillService;
import com.casacafemonteria.helpers.exception.exceptions.ResourceNotFoundException;
import com.casacafemonteria.product.persistence.entities.ProductEntity;
import com.casacafemonteria.product.persistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailBillServiceImpl implements IDetailBillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final DetailBillRepository billDetailRepository;
    private final DetailBillFactory factory;

    @Override
    @Transactional
    public DetailBillDto create(DetailBillPayload payload) {
        BillEntity bill = billRepository.findById(payload.getBillId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        ProductEntity product = productRepository.findById(payload.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        DetailBillEntity detail = DetailBillEntity.builder()
                .billEntity(bill)
                .productEntity(product)
                .quantity(payload.getQuantity())
                .priceUnitary(product.getPrice())
                .build();

        return factory.detailBillEntity(billDetailRepository.save(detail));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetailBillDto> getByBillId(Long billId) {
        BillEntity bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        return billDetailRepository.findByBillEntity(bill)
                .stream()
                .map(factory::detailBillEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DetailBillDto findById(Long id) {
        DetailBillEntity detail = billDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de factura no encontrado"));
        return factory.detailBillEntity(detail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        billDetailRepository.deleteById(id);
    }
}
