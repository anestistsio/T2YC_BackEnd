package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private int id;
    private String name;
    private List<SupportAgentMiniDto> agentsThatSupportThisProduct;
    private boolean isActive;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.agentsThatSupportThisProduct = product.getAgentsThatSupportThisProduct().stream()
                .map(SupportAgentMiniDto::new)
                .toList();
        this.isActive = product.isActive();
    }
}
