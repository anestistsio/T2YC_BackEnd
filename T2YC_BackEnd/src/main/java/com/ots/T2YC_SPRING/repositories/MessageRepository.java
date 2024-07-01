package com.ots.T2YC_SPRING.repositories;

import com.ots.T2YC_SPRING.entities.Message;
import com.ots.T2YC_SPRING.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT m FROM Message m WHERE m.senderId = :senderId AND m.senderRole = :senderRole")
    List<Message> findAllBySender(@Param("senderId") int senderId, @Param("senderRole") Role senderRole);
}
