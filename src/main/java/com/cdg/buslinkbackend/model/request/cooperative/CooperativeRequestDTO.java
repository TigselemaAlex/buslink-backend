package com.cdg.buslinkbackend.model.request.cooperative;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class is used to create a new cooperative
 */
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

    @NotNull(message = "La cantidad máxima de buses de la cooperativa no puede estar vacia.")
    @Min(value = 1, message = "La cantidad mínima permitida por cada cooperativa es de 1")
    private Integer max;

    @NotNull(message = "El estado no puede estar vacio")
    private Boolean status;

    private MultipartFile image;

    // A constructor.
    public CooperativeRequestDTO(String name, String phone, String address, Integer max, MultipartFile image) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.max = max;
        this.image = image;
    }
}
