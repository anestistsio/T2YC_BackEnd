package com.ots.T2YC_SPRING.security.auth;


import com.ots.T2YC_SPRING.entities.Customer;
import com.ots.T2YC_SPRING.entities.Role;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotActiveException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.CustomerRepository;
import com.ots.T2YC_SPRING.repositories.SupportAgentRepository;
import com.ots.T2YC_SPRING.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SupportAgentRepository supportAgentRepository;

    public AuthenticationResponse register(RegisterRequest request) throws ExistingException{
        if (customerRepository.findByEmail(request.getEmail()).isEmpty()) {
            var customer = Customer.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            customer.setActive(true);
            customerRepository.save(customer);
            var jwtToken = jwtService.generateToken(customer);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else {
           throw new ExistingException("The customer with email " + request.getEmail() + " already exists");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (request.getRole().equals(Role.CUSTOMER)){
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
            }catch (AuthenticationException e) {
                throw new NotFoundException("Invalid email or password");
            }

            var customer = customerRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new NotFoundException("There is no customer with this email"));
            if (customer.isActive()) {
                var jwtToken = jwtService.generateToken(customer);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }else {
                throw new NotActiveException("This Customer Is Not Active");
            }

        }else {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var supportAgent = supportAgentRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new NotFoundException("There is no support agent with this email"));
            var jwtToken = jwtService.generateToken(supportAgent);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }

}
