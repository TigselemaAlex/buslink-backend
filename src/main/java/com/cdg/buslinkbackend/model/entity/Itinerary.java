package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "itineraries")
public class Itinerary {

    @Id
    private String id;
    @DocumentReference
    private Bus bus;
    @DocumentReference
    private Frequency  frequency;
    private String departureTime;

    public Itinerary(Bus bus, Frequency frequency, String departureTime) {
        this.bus = bus;
        this.frequency = frequency;
        this.departureTime = departureTime;
    }
}
