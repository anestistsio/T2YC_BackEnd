package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.SupportAgent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportAgentMiniDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int yearsOfExperience;
    private boolean isActive;

    public SupportAgentMiniDto (SupportAgent supportAgent){
        this.id = supportAgent.getId();
        this.firstName = supportAgent.getFirstName();
        this.lastName = supportAgent.getLastName();
        this.email = supportAgent.getEmail();
        this.yearsOfExperience = supportAgent.getYearsOfExperience();
        this.isActive = supportAgent.isActive();
    }

}
