package com.cdg.buslinkbackend.model.response.itinerary;

import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.response.bus.BusResponseDTO;
import lombok.*;

/**
 * ItineraryResponseDTO is a class that has an id, a bus, a frequency, and a
 * departureTime
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItineraryResponseDTO {

    private String id;
    private BusResponseDTO bus;

    private Frequency frequency;
    private String departureTime;
}
