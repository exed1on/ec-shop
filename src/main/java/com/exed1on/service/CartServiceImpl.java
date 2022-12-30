package com.exed1on.service;

import com.exed1on.dto.CartDTO;
import com.exed1on.dto.CartDetailDTO;
import com.exed1on.model.Cart;
import com.exed1on.model.Product;
import com.exed1on.repository.CartRepository;
import com.exed1on.repository.ProductRepository;
import com.exed1on.model.User;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode
@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final UserService userService;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Cart createCart(User user, List<Long> productIds) {
        Cart cart = new Cart();
        cart.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        cart.setProducts(productList);
        return cartRepository.save(cart);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProducts(Cart cart, List<Long> productIds) {
        List<Product> products = cart.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        cart.setProducts(newProductList);
        cartRepository.save(cart);
    }

    @Override
    public void removeProduct(Cart cart, List<Long> productIds) {
//        List<Product> products = cart.getProducts();
//        if(products.contains(productId)){
//            products.stream()
//                    .findFirst()
//                    .filter(product -> product.getId().equals(productId));
//            products.removeIf(product -> product.getId().equals(productId));
//
//
//        }
//        Cart newCart = new Cart();
//        newCart.setProducts(products);
//        cartRepository.save(newCart);
        List<Product> products = cart.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        cart.setProducts(newProductList);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getCart() == null){
            return new CartDTO();
        }

        CartDTO cartDto = new CartDTO();
        Map<Long, CartDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getCart().getProducts();
        for (Product product : products) {
            CartDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new CartDetailDTO(product));
            }
            else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }

        cartDto.setCartDetails(new ArrayList<>(mapByProductId.values()));
        cartDto.aggregate();

        return cartDto;
    }
}
