package com.cdg.buslinkbackend.model.request.itinerary;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItinerarySearchDTO {

    private String origen;

    private String destiny;
}
