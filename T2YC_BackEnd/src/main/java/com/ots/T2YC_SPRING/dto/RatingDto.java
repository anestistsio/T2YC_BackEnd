package com.ots.T2YC_SPRING.dto;

import com.ots.T2YC_SPRING.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {

    private int id;
    private int rate;
    private String comment;

    public RatingDto(Rating rating){
        this.id = rating.getId();
        this.rate = rating.getRate();
        this.comment = rating.getComment();
    }
}
