package com.exed1on.controller;

import com.exed1on.dto.CartDTO;
import com.exed1on.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String aboutCart(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("cart", new CartDTO());
        }
        else {
            CartDTO cartDTO = cartService.getCartByUser(principal.getName());
            model.addAttribute("cart", cartDTO);
        }

        return "cart";
    }


}
