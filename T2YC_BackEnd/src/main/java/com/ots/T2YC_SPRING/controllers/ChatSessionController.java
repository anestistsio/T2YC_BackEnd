package com.ots.T2YC_SPRING.controllers;

import com.ots.T2YC_SPRING.dto.ChatSessionDto;
import com.ots.T2YC_SPRING.services.ChatSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat_session")
public class ChatSessionController {

    private final ChatSessionService chatSessionService;

    public ChatSessionController(ChatSessionService chatSessionService) {
        this.chatSessionService = chatSessionService;
    }
    @GetMapping("/customer/{id}")
    public List<ChatSessionDto> getAllChatSessionsOfThisCustomer(@PathVariable int id){
        return chatSessionService.findChatSessionByCustomerId(id);
    }
    @GetMapping("/agent/{id}")
    public List<ChatSessionDto> getAllChatSessionsOfThisSupportAgent(@PathVariable int id){
        return chatSessionService.findChatSessionBySupportAgentId(id);
    }
    @GetMapping("/product/{id}")
    public List<ChatSessionDto> getAllChatSessionsOfThisProduct(@PathVariable int id){
        return chatSessionService.findChatSessionByProductId(id);
    }
    @PostMapping("/new")
    public ResponseEntity<?> createNewChatSession(@RequestBody ChatSessionDto chatSessionDto){
        chatSessionService.createNewChatSession(chatSessionDto);
        return ResponseEntity.ok("");
    }
}
