package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMiniDto {

    private int id;
    private String name;

    public ProductMiniDto(Product product){
        this.id = product.getId();
        this.name = product.getName();
    }
}
