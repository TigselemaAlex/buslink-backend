package com.cdg.buslinkbackend.model.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Itinerary {
    private Frequency  frequency;
    private String departure_time;
    private List<Integer> available_seats;
}
