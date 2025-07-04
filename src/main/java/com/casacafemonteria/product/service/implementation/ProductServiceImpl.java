package com.casacafemonteria.product.service.implementation;

import com.casacafemonteria.product.factory.ProductFactory;
import com.casacafemonteria.product.persistence.entities.ProductEntity;
import com.casacafemonteria.product.persistence.repositories.ProductRepository;
import com.casacafemonteria.product.presentation.dto.ProductDto;
import com.casacafemonteria.product.presentation.payload.ProductPayload;
import com.casacafemonteria.product.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {


    private final ModelMapper modelMapper;
    private final ProductFactory productFactory;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void save(ProductPayload productPayload) {
            ProductEntity productEntity = ProductEntity.builder()
                    .name(productPayload.getName())
                    .description(productPayload.getDescription())
                    .price(productPayload.getPrice())
                    .stock(productPayload.getStock() == null ? 0 : productPayload.getStock())
                    .imageUrl(productPayload.getImageUrl() == null ? "" : productPayload.getImageUrl())
                    .build();

            productRepository.save(productEntity);
    }

    @Override
    @Transactional
    public void update(ProductPayload productPayload, Long id) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product no encontrado con el ID: " + id ));

            modelMapper.map(productPayload, existing);
            productRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product no encontrado con el ID: " + id ));
        productRepository.delete(productEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productFactory::productDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> searchByName(String name, Pageable pageable) {
        List<ProductEntity> results = productRepository.findByNameContainingIgnoreCase(name);
        List<ProductDto> dtos = results.stream().map(productFactory::productDto).toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(productFactory::productDto)
                .orElseThrow(() -> new RuntimeException("Product no encontrado con el ID: " + id ));
    }
}
