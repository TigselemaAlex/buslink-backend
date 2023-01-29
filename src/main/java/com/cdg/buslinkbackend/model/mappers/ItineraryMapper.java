package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.entity.Itinerary;
import com.cdg.buslinkbackend.model.response.itinerary.ItineraryResponseDTO;


public class ItineraryMapper {

    public static Itinerary itineraryFromItineraryRequestDTO(String departureTime, Bus bus, Frequency frequency){
        return Itinerary.builder()
                .bus(bus)
                .departureTime(departureTime)
                .frequency(frequency)
                .build();
    }

    public static ItineraryResponseDTO itineraryResponseDTOFromItinerary (Itinerary itinerary){
        return ItineraryResponseDTO.builder()
                .id(itinerary.getId())
                .bus(BusMapper.busResponseDTOFromBus(itinerary.getBus()))
                .departureTime(itinerary.getDepartureTime())
                .frequency(itinerary.getFrequency())
                .build();
    }
}
