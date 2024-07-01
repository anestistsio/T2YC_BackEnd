package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.CustomerDto;
import com.ots.T2YC_SPRING.entities.Customer;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach()
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void allCustomers() {
        Customer customer1 = Customer.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .isActive(true)
                .build();
        Customer customer2 = Customer.builder()
                .id(2)
                .firstName("testFirstName2")
                .lastName("testLastName2")
                .email("testEmail2")
                .isActive(true)
                .build();
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDto> result = customerService.allCustomers();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findCustomerById() {
        Customer customer = Customer.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .isActive(true)
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.findCustomerById(1);


        assertNotNull(result);
        assertEquals(1,result.getId());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void findCustomerById_NotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.findCustomerById(1));

        assertEquals("Customer not found with id: 1", exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void createCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .build();
        Customer customer = new Customer(customerDto);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteCustomerById() {
        Customer customer = Customer.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .isActive(true)
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        customerService.deactivateCustomerById(1);

        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteCustomerById_NotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.deactivateCustomerById(1));

        assertEquals("Customer not found with id: 1", exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(0)).deleteById(1);
    }

    @Test
    void updateCustomer() {
        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .build();

        Customer customer = new Customer(updatedCustomerDto);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.updateCustomer(1, updatedCustomerDto);

        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_NotFound() {
        CustomerDto updatedCustomerDto = CustomerDto.builder()
                .id(1)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.updateCustomer(1, updatedCustomerDto));

        assertEquals("Customer not found with id: 1", exception.getMessage());
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }
}
