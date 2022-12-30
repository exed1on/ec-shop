package com.exed1on.controller;

import com.exed1on.dto.CartDTO;
import com.exed1on.model.Cart;
import com.exed1on.model.User;
import com.exed1on.service.CartService;
import com.exed1on.service.ProductService;
import com.exed1on.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CartController {
    private final CartService cartService;

    private final ProductService productService;
    private final UserService userService;

    public CartController(CartService cartService, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String aboutCart(Model model, Principal principal){
//        List<ProductDTO> list = productService.getAll();
//        model.addAttribute("products", list);
        if(principal == null){
            model.addAttribute("cart", new CartDTO());
        }
        else {
            CartDTO cartDTO = cartService.getCartByUser(principal.getName());
            model.addAttribute("cart", cartDTO);
        }

        return "cart";
    }

    @GetMapping("/{id}/cart")
    public String addCart(@PathVariable Long id, Principal principal){
        if(principal==null){
            return "redirect:/cart";
        }
        productService.addToUserCart(id, principal.getName());
        return "redirect:/cart";
    }

    @PostMapping("/cart")
    public String createOrder(Model model, Principal principal){
        if(principal == null){
            throw new RuntimeException("You are not logged in");
        }
        User user = userService.findByName(principal.getName());
        model.addAttribute("user", user);
        Cart cart = user.getCart();
        if(cart==null){
            throw new RuntimeException("Your cart is empty");
        }
        else {
            CartDTO cartDTO = cartService.getCartByUser(principal.getName());
            model.addAttribute("cart", cartDTO);
        }
        return "orderCreated";
    }
}
