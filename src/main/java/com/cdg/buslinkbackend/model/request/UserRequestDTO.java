package com.cdg.buslinkbackend.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequestDTO {
    @NotBlank(message = "La cédula no puede estar en blanco")
    @Size(min = 10, max = 10, message = "La cédula debe tener 10 caracteres.")
    private String ci;

    @NotBlank(message = "El nombre no puede estar en blanco")
    private String full_name;

    @NotBlank(message = "El teléfono no puede estar en blanco")
    private String phone;

    @NotBlank(message = "La ciudad no puede estar en blanco")
    private String city;

    private boolean status;

    @NotBlank(message = "El usuario no puede estar en blanco")
    @Size(min = 5, max = 20, message = "El usuario debe tener entre 5 a 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 6, max = 12, message = "La contraseña debe tener entre 6 a 12 caracteres")
    private String password;

    @NotBlank(message = "El rol no puede estar en blanco")
    private String role_id;



}
