package com.cdg.buslinkbackend.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class BusUserResponseDTO extends UserResponseDTO{
    private String coop_id;
}
