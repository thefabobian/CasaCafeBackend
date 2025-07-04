package com.casacafemonteria.itemCart.service.interfaces;

import com.casacafemonteria.itemCart.presentation.dto.ItemCartDto;
import com.casacafemonteria.itemCart.presentation.payload.ItemCartPayload;

import java.util.List;

public interface IItemCartService {
    ItemCartDto addItem(ItemCartPayload cartPayload);
    List<ItemCartDto> getItemsByCartId(Long cartId);
    void deleteItem(Long itemId);
    ItemCartDto updateItem(ItemCartPayload payload, Long id);
    /*ItemCartDto findById(Long id);*/
    ItemCartDto findByIdAndCart(Long itemId, Long cartId);
}
