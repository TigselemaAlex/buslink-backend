package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.request.frequency.FrequencyRequestDTO;
import com.cdg.buslinkbackend.model.response.frecuency.FrequencyResponseDTO;

public class FrequencyMapper {

    public static FrequencyResponseDTO frequencyResponseDTOFromFrequency(Frequency frequency){
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

    public static Frequency frequencyFromFrequencyRequestDTO(FrequencyRequestDTO frequencyRequestDTO){
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
