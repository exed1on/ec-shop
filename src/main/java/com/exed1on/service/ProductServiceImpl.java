package com.exed1on.service;

import com.exed1on.dto.ProductDTO;
import com.exed1on.mapper.ProductMapper;
import com.exed1on.model.Cart;
import com.exed1on.repository.ProductRepository;
import com.exed1on.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;


    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartService cartService;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService,
                              CartService cartService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
    }


    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    @Transactional
    public void addToUserCart(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found with username " + username);
        }
        Cart cart=user.getCart();
        if(cart==null){
            Cart newCart = cartService.createCart(user, Collections.singletonList(productId));
            user.setCart(newCart);
            userService.save(user);
        } else {
            cartService.addProducts(cart, Collections.singletonList(productId));
        }
    }
}
