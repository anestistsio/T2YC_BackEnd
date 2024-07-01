package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.Message;
import com.ots.T2YC_SPRING.entities.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private String timeStamp;
    private String content;
    private Role senderRole;
    private int senderId;

    public MessageDto(Message message) {
        this.timeStamp = message.getTimeStamp();
        this.content = message.getContent();
        this.senderRole = message.getSenderRole();
        this.senderId = message.getSenderId();
    }

    @Override
    public String toString() {
        return "{" +
                "content='" + content + '\'' +
                ",timeStamp='" + timeStamp + '\'' +
                ",senderRole=" + senderRole +
                ",senderId=" + senderId +
                '}';
    }
}
