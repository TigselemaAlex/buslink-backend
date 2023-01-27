package com.cdg.buslinkbackend.model.response.cooperative;

import com.cdg.buslinkbackend.model.response.frecuency.FrequencyResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CooperativeWithFrequenciesResponseDTO {

    public String id;
    private String name;

    private List<FrequencyResponseDTO> frequencies;
}
