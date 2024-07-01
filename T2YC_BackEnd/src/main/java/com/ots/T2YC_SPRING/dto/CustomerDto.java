package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private String password;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.isActive = customer.isActive();
    }
}
