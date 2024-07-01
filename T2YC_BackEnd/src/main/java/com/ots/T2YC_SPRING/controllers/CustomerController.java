package com.ots.T2YC_SPRING.controllers;

import com.ots.T2YC_SPRING.dto.CustomerDto;
import com.ots.T2YC_SPRING.entities.Customer;
import com.ots.T2YC_SPRING.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class  CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<CustomerDto> getAllCustomer(){
        return customerService.allCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable int id){
        return customerService.findCustomerById(id);
    }

    @GetMapping("/email/{email}")
    public CustomerDto getCustomerByEmail(@PathVariable String email){
        return customerService.findCustomerByEmail(email);
    }

    @PostMapping
    public ResponseEntity<String> addNewCustomer(@RequestBody CustomerDto newCustomerDto){
        if (newCustomerDto == null){
            return ResponseEntity.badRequest().body("Invalid request body");
        }
        Customer createdCustomer = customerService.createCustomer(newCustomerDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateCustomer(@PathVariable int id){
        customerService.deactivateCustomerById(id);
    }

    @PutMapping("/deactivate/all")
    public void deactivateAllCustomers(){
        customerService.deactivateAllCustomers();
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable int id,@RequestBody CustomerDto updatedCustomerDto){
        customerService.updateCustomer(id,updatedCustomerDto);
    }

}
