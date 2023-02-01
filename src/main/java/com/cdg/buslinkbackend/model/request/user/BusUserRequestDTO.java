package com.cdg.buslinkbackend.model.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BusUserRequestDTO extends UserRequestDTO and adds a new field called coop_id
 */
@Getter
@Setter
@NoArgsConstructor
public class BusUserRequestDTO extends UserRequestDTO {

    @NotBlank(message = "La cooperativa no puede estar vacia")
    private String coop_id;
}
