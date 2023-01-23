package com.cdg.buslinkbackend.model.response.frecuency;

import com.cdg.buslinkbackend.model.enums.FrequencyType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    List<String> stops = new ArrayList<>();
}
