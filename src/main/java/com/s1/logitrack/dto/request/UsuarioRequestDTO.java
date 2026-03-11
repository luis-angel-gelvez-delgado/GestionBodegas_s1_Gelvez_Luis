package com.s1.logitrack.dto.request;

import com.s1.logitrack.model.TipoRol;
import jakarta.validation.constraints.*;

public record UsuarioRequestDTO(

        @NotBlank(message = "El nombre no puede estar vacio")
        String nombre,

        @NotBlank(message = "El username no puede estar vacio")
        String username,

        @NotBlank(message = "La contrase;a no puede estar vacia")
        @Size(min = 4, message = "La password debe tener minimo 4 caracteres")
        String password,

        @NotNull(message = "El rol no puede ser nulo")
        TipoRol rol
) {}
