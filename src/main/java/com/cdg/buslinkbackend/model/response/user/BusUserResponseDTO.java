package com.cdg.buslinkbackend.model.response.user;

import com.cdg.buslinkbackend.model.response.cooperative.CooperativeResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class BusUserResponseDTO extends UserResponseDTO{
    private CooperativeResponseDTO cooperative;
}
