package com.ots.T2YC_SPRING.entities;

import com.ots.T2YC_SPRING.dto.ChatSessionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_session")
@Getter
@Setter
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "support_agent_id", referencedColumnName = "id")
    private SupportAgent supportAgent;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "chat_status" , columnDefinition = "ENUM('NEVER_STARTED', 'ON_GOING', 'FINISHED')")
    @Enumerated(EnumType.STRING)
    private ChatStatus chatStatus;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

}
