package com.ots.T2YC_SPRING.repositories;

import com.ots.T2YC_SPRING.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingsRepository extends JpaRepository<Rating,Integer> {
}
