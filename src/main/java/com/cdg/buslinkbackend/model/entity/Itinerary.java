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
@Document(collection = "itineraries")
public class Itinerary {

    @Id
    private String id;
    @DocumentReference
    private Bus bus;
    @DocumentReference
    private Frequency  frequency;
    private String departure_time;

}
