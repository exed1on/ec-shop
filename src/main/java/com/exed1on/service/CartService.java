package com.exed1on.service;

import com.exed1on.dto.CartDTO;
import com.exed1on.model.Cart;
import com.exed1on.model.User;

import java.util.List;

public interface CartService{
    Cart createCart(User user, List<Long> productIds);

    void addProducts(Cart cart, List<Long> productIds);

    void removeProduct(Cart cart, List<Long> productIds);

    CartDTO getCartByUser(String name);
}
