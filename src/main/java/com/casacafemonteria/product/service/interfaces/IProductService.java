package com.casacafemonteria.product.service.interfaces;

import com.casacafemonteria.product.presentation.dto.ProductDto;
import com.casacafemonteria.product.presentation.payload.ProductPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    void save(ProductPayload productPayload);
    void update(ProductPayload productPayload, Long id);
    void delete(Long id);
    Page<ProductDto> findAll(Pageable pageable);
    Page<ProductDto> searchByName(String name, Pageable pageable);
    ProductDto findById(Long id);
}
