package com.s1.logitrack.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoRequestDTO(

        @NotBlank(message = "El nombre no puede estar vacio")
        String nombre,

        @NotBlank(message = "La categoria no puede estar vacia")
        String categoria,

        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal precio
) {}