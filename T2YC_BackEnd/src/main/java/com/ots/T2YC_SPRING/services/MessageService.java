package com.ots.T2YC_SPRING.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.ots.T2YC_SPRING.dto.MessageDto;
import com.ots.T2YC_SPRING.entities.ChatSession;
import com.ots.T2YC_SPRING.entities.Message;
import com.ots.T2YC_SPRING.exceptions.NotFoundException;
import com.ots.T2YC_SPRING.repositories.ChatSessionRepository;
import com.ots.T2YC_SPRING.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final ChatSessionRepository chatSessionRepository;
    private final MessageRepository messageRepository;

    public MessageService(ChatSessionRepository chatSessionRepository, MessageRepository messageRepository) {
        this.chatSessionRepository = chatSessionRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageDto saveMessageFromMqtt (int chatSessionId, String messageJsonObj) throws JsonProcessingException {
        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElseThrow(() -> new NotFoundException("Chat Session " + chatSessionId + " doesn't exists!"));
        Message message = new Message();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDto messageDto;
        try {
            messageDto = objectMapper.readValue(messageJsonObj, MessageDto.class);
        } catch (UnrecognizedPropertyException e) {
            System.out.println("Unrecognized field: {}"+ e.getPropertyName() + e);
            return null; // Handle the response in the caller
        } catch (Exception e) {
            System.out.println("An error occurred while processing the message" + e);
            return null; // Handle the response in the caller
        }
        message.setChatSession(chatSession);
        message.setContent(messageDto.getContent());
        message.setSenderId(messageDto.getSenderId());
        message.setTimeStamp(messageDto.getTimeStamp());
        message.setSenderRole(messageDto.getSenderRole());
        messageRepository.save(message);
        return messageDto;
    }
}
