package com.cdg.buslinkbackend.model.request.bus;

import com.cdg.buslinkbackend.model.entity.Itinerary;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusRequestDTO {

    @NotBlank (message = "La marca no puede estar vacia")
    private String brand;
    @NotBlank (message = "El modelo no puede estar vacio")
    private String model;
    @NotBlank(message = "La placa no puede estar vacia")
    private String plate;

    @NotBlank(message = "El chasis no puede estar vacio")
    private String chassis;

    @NotNull(message = "El número del bus no puede estar vacio")
    @Min(value = 1, message = "El numero del bus debe ser mayor a 0")
    private Integer busNumber;
    @NotNull(message = "La cantidad de asientos no puede estar vacia")
    @Min(value = 30, message = "La cantidad minima de asientos es de 30")
    private Integer seatingNumber;
    private Double vipPrice;
    private Integer startVip;
    private Integer endVip;
    private List<Itinerary> itineraries;
    @NotBlank(message = "El id de la cooperativa no puede estar vacio")
    private String cooperative_id;
}
