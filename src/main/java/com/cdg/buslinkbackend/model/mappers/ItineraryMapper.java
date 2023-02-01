package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.entity.Itinerary;
import com.cdg.buslinkbackend.model.response.itinerary.ItineraryResponseDTO;

/**
 * It takes a departure time, a bus, and a frequency and returns an itinerary
 */
public class ItineraryMapper {

    /**
     * It takes a departure time, a bus, and a frequency and returns an itinerary
     * 
     * @param departureTime String
     * @param bus           Bus object
     * @param frequency     Frequency
     * @return Itinerary object
     */
    public static Itinerary itineraryFromItineraryRequestDTO(String departureTime, Bus bus, Frequency frequency) {
        return Itinerary.builder()
                .bus(bus)
                .departureTime(departureTime)
                .frequency(frequency)
                .build();
    }

    /**
     * It takes an Itinerary object and returns an ItineraryResponseDTO object
     * 
     * @param itinerary Itinerary object
     * @return ItineraryResponseDTO
     */
    public static ItineraryResponseDTO itineraryResponseDTOFromItinerary(Itinerary itinerary) {
        return ItineraryResponseDTO.builder()
                .id(itinerary.getId())
                .bus(BusMapper.busResponseDTOFromBus(itinerary.getBus()))
                .departureTime(itinerary.getDepartureTime())
                .frequency(itinerary.getFrequency())
                .build();
    }
}
