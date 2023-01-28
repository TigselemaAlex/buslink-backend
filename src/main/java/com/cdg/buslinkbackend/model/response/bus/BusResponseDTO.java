package com.cdg.buslinkbackend.model.response.bus;

import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.response.cooperative.CooperativeResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusResponseDTO {

    private String id;
    private String brand;
    private String model;
    private String plate;
    private String chassis;
    private Double vipPrice;
    private Integer busNumber;
    private List<Seating> seating;
    private CooperativeResponseDTO cooperative;
}
