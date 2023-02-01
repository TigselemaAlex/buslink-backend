package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.request.frequency.FrequencyRequestDTO;
import com.cdg.buslinkbackend.model.response.frecuency.FrequencyResponseDTO;

/**
 * It takes a frequency object and returns a frequencyResponseDTO object
 */
public class FrequencyMapper {

    /**
     * It takes a frequency object and returns a frequencyResponseDTO object
     * 
     * @param frequency Frequency
     * @return A FrequencyResponseDTO object
     */
    public static FrequencyResponseDTO frequencyResponseDTOFromFrequency(Frequency frequency) {
        return FrequencyResponseDTO.builder()
                .id(frequency.getId())
                .origen(frequency.getOrigen())
                .destiny(frequency.getDestiny())
                .stops(frequency.getStops())
                .price(frequency.getPrice())
                .type(frequency.getType())
                .hours(frequency.getHours())
                .minutes(frequency.getMinutes())
                .build();
    }

    /**
     * It takes a FrequencyRequestDTO object and returns a Frequency object
     * 
     * @param frequencyRequestDTO
     * @return A Frequency object
     */
    public static Frequency frequencyFromFrequencyRequestDTO(FrequencyRequestDTO frequencyRequestDTO) {
        return Frequency.builder()
                .origen(frequencyRequestDTO.getOrigen())
                .destiny(frequencyRequestDTO.getDestiny())
                .price(frequencyRequestDTO.getPrice())
                .stops(frequencyRequestDTO.getStops())
                .type(frequencyRequestDTO.getType())
                .hours(frequencyRequestDTO.getHours())
                .minutes(frequencyRequestDTO.getMinutes())
                .build();
    }
}
