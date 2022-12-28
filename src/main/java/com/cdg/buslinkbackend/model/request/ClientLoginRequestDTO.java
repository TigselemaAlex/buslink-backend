package com.cdg.buslinkbackend.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientLoginRequestDTO {

    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "Formato del email no válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 6, max = 12, message = "La contraseña debe tener entre 6 a 12 caracteres")
    private String password;
}
