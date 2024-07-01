package com.ots.T2YC_SPRING.repositories;

import com.ots.T2YC_SPRING.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByEmail(String email);

    @Query("SELECT cs.customer FROM ChatSession cs WHERE cs.id = :chatSessionId AND cs.customer.id = :sender")
    Optional<Customer> findCustomerByChatSessionId(@Param("chatSessionId") int sessionId, @Param("sender") int sender);

    Optional<Customer> findCustomerByFirstName(String name);

}
