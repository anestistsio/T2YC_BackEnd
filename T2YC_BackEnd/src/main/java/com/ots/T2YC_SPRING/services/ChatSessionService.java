package com.ots.T2YC_SPRING.services;

import com.ots.T2YC_SPRING.dto.ChatSessionDto;
import com.ots.T2YC_SPRING.entities.ChatSession;
import com.ots.T2YC_SPRING.entities.ChatStatus;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.ChatSessionRepository;
import com.ots.T2YC_SPRING.repositories.CustomerRepository;
import com.ots.T2YC_SPRING.repositories.ProductRepository;
import com.ots.T2YC_SPRING.repositories.SupportAgentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;
    private final SupportAgentRepository supportAgentRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public ChatSessionService(ChatSessionRepository chatSessionRepository, SupportAgentRepository supportAgentRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.chatSessionRepository = chatSessionRepository;
        this.supportAgentRepository = supportAgentRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<ChatSessionDto> findChatSessionByCustomerId(int customer_id){
        return chatSessionRepository.findByCustomerId(customer_id)
                .orElseThrow(() -> new NotFoundException("No chat sessions found for this customer"))
                .stream()
                .map(ChatSessionDto::new)
                .collect(Collectors.toList());
    }
    public List<ChatSessionDto> findChatSessionBySupportAgentId(int supportAgent_id){
        return chatSessionRepository.findBySupportAgentId(supportAgent_id)
                .orElseThrow(() -> new NotFoundException("No chat sessions found for this support agent"))
                .stream()
                .map(ChatSessionDto::new)
                .collect(Collectors.toList());
    }
    public List<ChatSessionDto> findChatSessionByProductId(int product_id){
        return chatSessionRepository.findByProductId(product_id)
                .orElseThrow(() -> new NotFoundException("No chat sessions found for this product"))
                .stream()
                .map(ChatSessionDto::new)
                .collect(Collectors.toList());
    }
    public ChatSession createNewChatSession (ChatSessionDto chatSessionDto){
        ChatSession chatSession = new ChatSession();
        chatSession.setSupportAgent(supportAgentRepository.findById(chatSessionDto.getSupportAgentId())
                .orElseThrow(() -> new NotFoundException("Support Agent with id: " + chatSessionDto.getSupportAgentId() + " doesn't exist!")));
        chatSession.setCustomer(customerRepository.findById(chatSessionDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer with id: " + chatSessionDto.getCustomerId() + " doesn't exist!")));
        chatSession.setProduct(productRepository.findById(chatSessionDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product with id: " + chatSessionDto.getProductId() + " doesn't exist!")));
        chatSession.setChatStatus(ChatStatus.ON_GOING);
        chatSession.setStartTime(LocalDateTime.now());
        return chatSessionRepository.save(chatSession);
    }
}
