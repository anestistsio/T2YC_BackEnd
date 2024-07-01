package com.ots.T2YC_SPRING.entities;

import com.ots.T2YC_SPRING.dto.ProductDto;
import com.ots.T2YC_SPRING.dto.ProductMiniDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="agent_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "agent_id")
    )
    private List<SupportAgent> agentsThatSupportThisProduct;

    public Product(ProductDto productDto) {
        this.id = productDto.getId();
        this.agentsThatSupportThisProduct = productDto.getAgentsThatSupportThisProduct().stream()
                .map(SupportAgent::new)
                .toList();
        this.name = productDto.getName();
    }
    public Product(ProductMiniDto productMiniDto) {
        this.id = productMiniDto.getId();
        this.name = productMiniDto.getName();
    }
}
