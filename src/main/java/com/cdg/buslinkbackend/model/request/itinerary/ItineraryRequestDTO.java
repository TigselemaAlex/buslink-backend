package com.cdg.buslinkbackend.model.request.itinerary;

import lombok.*;

import java.util.List;

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
