package com.cdg.buslinkbackend.model.request.itinerary;

import lombok.*;

import java.util.List;

/**
 * ItineraryRequestDTO is a class that has a String bus_id and a List of
 * ItineraryFrequencyDetailDTO
 * objects
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ItineraryRequestDTO {

    private String bus_id;
    private List<ItineraryFrequencyDetailDTO> frequencies;
}
