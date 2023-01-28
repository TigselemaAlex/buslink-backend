package com.cdg.buslinkbackend.model.request.itinerary;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItineraryFrequencyDetailDTO {

    private String frequency_id;

    private String departureTime;
}
