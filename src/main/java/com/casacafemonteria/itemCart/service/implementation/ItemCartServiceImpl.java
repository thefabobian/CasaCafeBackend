package com.casacafemonteria.itemCart.service.implementation;

import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.cart.persistence.repositories.CartRepository;
import com.casacafemonteria.helpers.exception.exceptions.ResourceNotFoundException;
import com.casacafemonteria.itemCart.factory.ItemCartFactory;
import com.casacafemonteria.itemCart.persistence.entities.ItemCartEntity;
import com.casacafemonteria.itemCart.persistence.repositories.ItemCartRepository;
import com.casacafemonteria.itemCart.presentation.dto.ItemCartDto;
import com.casacafemonteria.itemCart.presentation.payload.ItemCartPayload;
import com.casacafemonteria.itemCart.service.interfaces.IItemCartService;
import com.casacafemonteria.product.persistence.entities.ProductEntity;
import com.casacafemonteria.product.persistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCartServiceImpl implements IItemCartService {

    private final ItemCartRepository itemCartRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemCartFactory itemCartFactory;

    @Override
    @Transactional
    public ItemCartDto addItem(ItemCartPayload cartPayload) {
        CartEntity cartEntity = cartRepository.findById(cartPayload.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart no encontrado con el ID: " + cartPayload.getCartId()));

        ProductEntity productEntity = productRepository.findById(cartPayload.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product no encontrado con el ID: " + cartPayload.getProductId()));

        Optional<ItemCartEntity> existingItemOpt = itemCartRepository.findByCartEntity(cartEntity).stream()
                .filter(item -> item.getProductEntity().getId().equals(productEntity.getId()))
                .findFirst();

        ItemCartEntity itemCartEntity;

        if (existingItemOpt.isPresent()) {
            itemCartEntity = existingItemOpt.get();
            itemCartEntity.setQuantity(itemCartEntity.getQuantity() + cartPayload.getQuantity());
        } else {
            itemCartEntity = ItemCartEntity.builder()
                    .cartEntity(cartEntity)
                    .productEntity(productEntity)
                    .quantity(cartPayload.getQuantity())
                    .build();
        }

        return itemCartFactory.itemCartDto(itemCartRepository.save(itemCartEntity));
    }

    @Override
    public List<ItemCartDto> getItemsByCartId(Long cartId) {
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart no encontrado con el ID: " + cartId));

        return itemCartRepository.findByCartEntity(cartEntity)
                .stream()
                .map(itemCartFactory::itemCartDto)
                .toList();
    }

    @Override
    public void deleteItem(Long itemId) {
        itemCartRepository.deleteById(itemId);
    }

    @Override
    @Transactional
    public ItemCartDto updateItem(ItemCartPayload payload, Long id) {
        ItemCartEntity itemCart = itemCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCart no encontrado con ID: " + id));

        CartEntity cart = cartRepository.findById(payload.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart no encontrado con ID: " + payload.getCartId()));

        ProductEntity product = productRepository.findById(payload.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + payload.getProductId()));

        itemCart.setCartEntity(cart);
        itemCart.setProductEntity(product);
        itemCart.setQuantity(payload.getQuantity());

        return itemCartFactory.itemCartDto(itemCartRepository.save(itemCart));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemCartDto findByIdAndCart(Long itemId, Long cartId) {
        ItemCartEntity itemCartEntity = itemCartRepository.findByIdAndCartEntity_Id(itemId, cartId)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCart no encontrado con ID: " + itemId));
        return itemCartFactory.itemCartDto(itemCartEntity);
    }
}
