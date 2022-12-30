package com.exed1on.controller;

import com.exed1on.dto.CartDTO;
import com.exed1on.dto.ProductDTO;
import com.exed1on.service.CartService;
import com.exed1on.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;

    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
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
    @GetMapping("/{id}/remove")
    public String removeProduct(@PathVariable Long id, Principal principal){
        if(principal==null){
            return "redirect:/cart";
        }
        productService.removeFromUserCart(id, principal.getName());
        return "redirect:/cart";
    }
}
