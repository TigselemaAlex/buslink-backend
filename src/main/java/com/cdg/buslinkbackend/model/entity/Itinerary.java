package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Itinerary {
    @DBRef
    private Frequency  frequency;
    private String departure_time;
    private List<Integer> available_seats;
}
