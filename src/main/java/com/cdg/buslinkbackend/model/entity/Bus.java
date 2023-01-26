package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "buses")
public class Bus {

    @Id
    private String id;
    private String brand;
    private String model;
    private String plate;
    private String chassis;
    private Integer busNumber;
    private Integer seatingNumber;
    @Value("0.0")
    private Double vipPrice;
    public List<Seating> seating;
    private List<Itinerary> itineraries;
    private Cooperative cooperative;


}
