package com.ots.T2YC_SPRING.entities;

import com.ots.T2YC_SPRING.dto.RatingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ratings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "rate")
    private int rate;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "rated_agent",referencedColumnName = "id")
    private SupportAgent ratedAgent;

    public Rating(RatingDto ratingDto){
        this.id = ratingDto.getId();
        this.rate = ratingDto.getRate();
        this.comment = ratingDto.getComment();
    }
}
