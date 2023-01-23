package com.cdg.buslinkbackend.model.request.frequency;

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
public class FrequencyRequestDTO {

    private String origen;

    private String destiny;

    private FrequencyType type;

    private Double price;

    List<String> stops = new ArrayList<>();
}
