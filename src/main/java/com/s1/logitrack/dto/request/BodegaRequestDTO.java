package com.s1.logitrack.dto.request;

import jakarta.validation.constraints.*;

public record BodegaRequestDTO(

        @NotBlank(message = "El nombre no puede estar vacio")
        @Size(min = 3, max = 100)
        String nombre,

        @NotBlank(message = "La ubicacion no puede estar vacia")
        String ubicacion,

        @NotNull(message = "La capacidad no puede ser nula")
        @Min(value = 1, message = "La capacidad debe ser mayor a 0")
        Integer capacidad,

        @NotBlank(message = "El encargado no puede estar vacio")
        String encargado
) {}