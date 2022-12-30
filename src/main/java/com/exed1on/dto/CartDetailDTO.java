package com.exed1on.dto;

import com.exed1on.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailDTO {
    private String title;
    private Long productId;
    private Double price;
    private Integer amount;
    private Double sum;

    public CartDetailDTO(Product product){
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = 1;
        this.sum = product.getPrice();
    }
}
