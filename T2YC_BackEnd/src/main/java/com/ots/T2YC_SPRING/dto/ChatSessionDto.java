package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.ChatSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSessionDto {

    private int customerId;
    private int supportAgentId;
    private int productId;

    public ChatSessionDto(ChatSession chatSession) {
        this.customerId = chatSession.getCustomer().getId();
        this.supportAgentId = chatSession.getSupportAgent().getId();
        this.productId = chatSession.getProduct().getId();
    }

}
