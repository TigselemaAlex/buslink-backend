package com.cdg.buslinkbackend.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
