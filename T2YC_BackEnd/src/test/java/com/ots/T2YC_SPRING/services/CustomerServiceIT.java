package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.CustomerDto;
import com.ots.T2YC_SPRING.entities.Customer;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceIT {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    void allCustomers() {
        List<CustomerDto> result = customerService.allCustomers();
        int size = customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList()
                .size();
        assertEquals(size,result.size());
    }
    @Test
    void findCustomerById() {
        CustomerDto result = customerService.findCustomerById(2);
        assertNotNull(result);
        assertEquals("Bob",result.getFirstName());
    }
    @Test
    void findCustomerById_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.findCustomerById(99));
        assertEquals("Customer not found with id: 99", exception.getMessage());
    }
    @Test
    void createCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail1")
                .build();

        Customer result = customerService.createCustomer(customerDto);
        assertEquals("testFirstName",result.getFirstName());
    }
    @Test
    void tryToCreateExistingCustomer(){
        CustomerDto customerDto = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("alice.johnson@example.com")
                .build();
        ExistingException exception = assertThrows(ExistingException.class, () -> customerService.createCustomer(customerDto));
    }
    @Test
    void deactivateCustomerById() {
        customerService.deactivateCustomerById(1);
        assertFalse(customerRepository.findById(1).orElseThrow(() -> new NotFoundException("")).isActive());

       }
    @Test
    void deactivateCustomerById_NotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.deactivateCustomerById(99));
        assertEquals("Customer not found with id: 99", exception.getMessage());

    }
    @Test
    void deactivateAllCustomers() {
        customerService.deactivateAllCustomers();
        List<Customer> allCustomers = customerRepository.findAll()
                .stream()
                .toList();
        for (Customer customer : allCustomers ) {
            assertFalse(customer.isActive());
        }
    }
    @Test
    void updateCustomer() {
        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail2")
                .build();

        customerService.updateCustomer(1, updatedCustomerDto);
        Customer customer = customerRepository.findById(1).orElseThrow();
        assertEquals(customer.getFirstName(),updatedCustomerDto.getFirstName());
     }
    @Test
    void updateCustomer_NotFound() {
        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail3")
                .build();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.updateCustomer(99, updatedCustomerDto));
        assertEquals("Customer not found with id: 99", exception.getMessage());
       }

}