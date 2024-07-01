package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.SupportAgent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportAgentDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int yearsOfExperience;
    private List<ProductMiniDto> productThatAgentSupports;
    private boolean isActive;
    private List<RatingDto> ratings;

    public SupportAgentDto(SupportAgent supportAgent) {
        this.id = supportAgent.getId();
        this.email = supportAgent.getEmail();
        this.firstName = supportAgent.getFirstName();
        this.lastName = supportAgent.getLastName();
        this.productThatAgentSupports = supportAgent.getProductThatAgentSupports()
                .stream()
                .map(ProductMiniDto::new)
                .toList();
        this.yearsOfExperience = supportAgent.getYearsOfExperience();
        this.isActive = supportAgent.isActive();
        this.ratings = supportAgent.getRatings()
                .stream()
                .map(RatingDto::new)
                .toList();
    }
}
