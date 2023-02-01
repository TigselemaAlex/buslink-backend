package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

/**
 * Bus has a list of seating, and each seating has a list of passengers.
 */
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

    private Double vipPrice;
    private List<Seating> seating;
    @DocumentReference
    private Cooperative cooperative;

}
