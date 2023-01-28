package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.entity.Itinerary;


public class ItineraryMapper {

    public static Itinerary itineraryFromItineraryRequestDTO(String departureTime, Bus bus, Frequency frequency){
        return Itinerary.builder()
                .bus(bus)
                .departureTime(departureTime)
                .frequency(frequency)
                .build();
    }
}
