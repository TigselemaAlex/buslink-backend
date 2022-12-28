package com.cdg.buslinkbackend.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusUserRequestDTO extends UserRequestDTO{

    @NotBlank(message = "La cooperativa no puede estar vacia")
    private String coop_id;
}
