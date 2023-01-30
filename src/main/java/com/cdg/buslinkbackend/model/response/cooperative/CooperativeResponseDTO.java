package com.cdg.buslinkbackend.model.response.cooperative;

import com.cdg.buslinkbackend.model.response.frecuency.FrequencyResponseDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CooperativeResponseDTO {

    private String id;

    private String name;

    private Boolean status;

    private String phone;

    private String address;

    private Integer max;

    private List<FrequencyResponseDTO>  frequencies;
    private byte[] image;
}
