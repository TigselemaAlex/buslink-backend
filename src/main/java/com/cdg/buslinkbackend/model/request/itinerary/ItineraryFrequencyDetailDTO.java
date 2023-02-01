package com.cdg.buslinkbackend.model.request.itinerary;

import lombok.*;

/**
 * It's a DTO that contains a frequency_id and a departureTime.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItineraryFrequencyDetailDTO {

    private String frequency_id;

    private String departureTime;
}
