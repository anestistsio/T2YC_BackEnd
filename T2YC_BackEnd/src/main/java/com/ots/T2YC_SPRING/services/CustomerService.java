package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.CustomerDto;
import com.ots.T2YC_SPRING.entities.Customer;
import com.ots.T2YC_SPRING.exceptions.ExistingException;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDto> allCustomers(){
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .map(CustomerDto::new)
                .toList();
    }
    public CustomerDto findCustomerById(int id){
        return new CustomerDto(customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id)));
    }

    public CustomerDto findCustomerByEmail(String email){
        return new CustomerDto(customerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Customer not found with email: " + email)));
    }

    @Transactional
    public Customer createCustomer(CustomerDto customerDto){
        Customer newCustomer = new Customer(customerDto);
        if (customerRepository.findByEmail(newCustomer.getEmail()).isPresent()){
            throw new ExistingException("This customer email is used " + newCustomer.getEmail());
        }else {
            newCustomer.setActive(true);
            return customerRepository.save(newCustomer);
        }
    }
    @Transactional
    public void deactivateCustomerById(int id){
        Customer deactivatedCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
        deactivatedCustomer.setActive(false);
        customerRepository.save(deactivatedCustomer);
    }
    @Transactional
    public void deactivateAllCustomers(){
        List<Customer> allActiveCustomers = customerRepository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();
        if (!allActiveCustomers.isEmpty()) {
            for (Customer customer : allActiveCustomers) {
                customer.setActive(false);
                customerRepository.save(customer);
            }
        }else {
            throw new NotFoundException("There is not any active customer");
        }
    }

    @Transactional
    public void updateCustomer(int id,CustomerDto updatedCustomerDto){
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if(foundCustomer.isPresent()){
            Customer updatedCustomer = new Customer(updatedCustomerDto);
            updatedCustomer.setId(id);
            updatedCustomer.setPassword(passwordEncoder.encode(updatedCustomerDto.getPassword()));
            customerRepository.save(updatedCustomer);
        }else {
            throw new NotFoundException("Customer not found with id: " + id);
        }
    }

}
