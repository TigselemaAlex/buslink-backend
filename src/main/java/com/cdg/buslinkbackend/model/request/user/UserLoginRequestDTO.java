package com.cdg.buslinkbackend.model.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is a DTO (Data Transfer Object) that is used to receive the
 * username and password from
 * the user when they login
 */
@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDTO {

    @NotBlank(message = "El usuario no puede estar en blanco")
    @Size(min = 5, max = 20, message = "El usuario debe tener entre 5 a 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 6, max = 12, message = "La contraseña debe tener entre 6 a 12 caracteres")
    private String password;
}
