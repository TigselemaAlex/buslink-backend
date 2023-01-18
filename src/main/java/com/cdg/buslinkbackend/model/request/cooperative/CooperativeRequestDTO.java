package com.cdg.buslinkbackend.model.request.cooperative;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class CooperativeRequestDTO {

    @NotBlank(message = "El nombre de la cooperativa no puede estar vacio.")
    private String name;

    @NotBlank(message = "El teléfono de la cooperativa no puede estar vacio.")
    private String phone;

    @NotBlank(message = "La dirección de la cooperativa no puede estar vacia.")
    private String address;

    @NotEmpty(message = "La cantidad máxima de buses de la cooperativa no puede estar vacia.")
    @Size(min = 1, message = "La cantidad mínima permitida por cada cooperativa es de 1")
    private Integer max;

    private MultipartFile image;
}
