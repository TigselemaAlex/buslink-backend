package com.cdg.buslinkbackend.model.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientRegisterRequestDTO {
    @NotBlank(message = "La cédula no puede estar en blanco")
    @Size(min = 10, max = 10, message = "La cédula debe tener 10 caracteres.")
    private String ci;

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String full_name;

    @NotBlank(message = "El teléfono no puede estar en blanco")
    private String phone;

    @NotBlank(message = "La ciudad no puede estar en blanco")
    private String city;


    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email no tiene el formato adecuado")
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 6, max = 12, message = "La contraseña debe tener entre 6 a 12 caracteres")
    private String password;

}
