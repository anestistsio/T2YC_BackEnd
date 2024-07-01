package com.ots.T2YC_SPRING.repositories;

import com.ots.T2YC_SPRING.entities.ChatSession;
import com.ots.T2YC_SPRING.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession,Integer> {
    Optional<List<ChatSession>> findByCustomerId(Integer customerId);
    Optional<List<ChatSession>> findBySupportAgentId(Integer supportAgentId);
    Optional<List<ChatSession>> findByProductId(Integer productId);
    @Query("SELECT m FROM Message m WHERE m.chatSession.id = :chatSessionId")
    Optional<List<Message>> getAllMessagesOfThisChatSession(@Param("chatSessionId") Integer chatSessionId);
}
