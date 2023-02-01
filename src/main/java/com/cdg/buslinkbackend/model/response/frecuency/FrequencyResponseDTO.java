package com.cdg.buslinkbackend.model.response.frecuency;

import com.cdg.buslinkbackend.model.enums.FrequencyType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * FrequencyResponseDTO is a class that has a String id, a String origen, a
 * String destiny, a
 * FrequencyType type, a Double price, an Integer hours, an Integer minutes, and
 * a List of String stops
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrequencyResponseDTO {

    private String id;
    private String origen;

    private String destiny;

    private FrequencyType type;

    private Double price;

    private Integer hours;

    private Integer minutes;

    List<String> stops = new ArrayList<>();
}
