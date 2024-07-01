package com.ots.T2YC_SPRING.security.auth;

import com.ots.T2YC_SPRING.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private Role role;
    private String email;
    String password;

}
