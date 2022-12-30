package com.exed1on.service;

import com.exed1on.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();

    void addToUserCart(Long productId, String username);

    void removeFromUserCart(Long productId, String username);
}
