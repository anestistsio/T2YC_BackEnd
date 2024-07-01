package com.ots.T2YC_SPRING.entities;

import com.ots.T2YC_SPRING.dto.MessageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "time_stamp")
    private String timeStamp;
    @Column(name = "content")
    private String content;
    @Column(name = "sender_id")
    private int senderId;
    @ManyToOne
    @JoinColumn(name = "chat_session_id", referencedColumnName = "id")
    private ChatSession chatSession;
    @Column(name = "sender_role" , columnDefinition = "ENUM('CUSTOMER','SUPPORT_AGENT')")
    @Enumerated(EnumType.STRING)
    private Role senderRole;

    public Message(MessageDto messageDto){
        this.timeStamp = messageDto.getTimeStamp();
        this.content = messageDto.getContent();
        this.senderRole = messageDto.getSenderRole();
        this.senderId = messageDto.getSenderId();
    }

}
