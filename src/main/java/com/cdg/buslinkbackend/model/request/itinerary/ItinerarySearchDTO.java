package com.cdg.buslinkbackend.model.request.itinerary;

import lombok.*;

/**
 * It's a DTO class that contains the origin and destiny of a flight
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItinerarySearchDTO {

    private String origen;

    private String destiny;
}
