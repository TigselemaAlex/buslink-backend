package com.cdg.buslinkbackend.model.request.frequency;

import com.cdg.buslinkbackend.model.enums.FrequencyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrequencyRequestDTO {

    @NotBlank(message = "El origen no puede estar vacio")
    private String origen;

    @NotBlank(message = "El destino no puede estar vacio")
    private String destiny;

    @NotNull(message = "El tipo no puede estar vacio")
    private FrequencyType type;

    @NotNull(message = "El precio base no puede estar vacio")
    @DecimalMin( value = "0.01", message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "Las horas no pueden estar vacias")
    @Range(min = 0 , max = 23, message = "Las horas debe estar entre 0 y 23")
    private Integer hours;

    @NotNull(message = "Los minutos no pueden estar vacios")
    @Range(min = 0 , max = 59, message = "Los minutos deben estar entre 0 y 59")
    private Integer minutes;

    List<String> stops = new ArrayList<>();
}
