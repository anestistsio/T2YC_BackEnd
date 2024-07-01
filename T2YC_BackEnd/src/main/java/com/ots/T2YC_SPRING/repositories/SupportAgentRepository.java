package com.ots.T2YC_SPRING.repositories;

import com.ots.T2YC_SPRING.entities.SupportAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SupportAgentRepository extends JpaRepository<SupportAgent,Integer> {
    Optional<SupportAgent> findByEmail(String email);
}
