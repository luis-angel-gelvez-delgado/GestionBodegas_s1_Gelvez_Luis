package com.s1.logitrack.dto.request;

import jakarta.validation.constraints.*;

public record DetalleMovimientoRequestDTO(

        @NotNull(message = "El producto no puede ser nulo")
        Long productoId,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 1, message = "La cantidad debe ser mayor a 0")
        Integer cantidad
) {}
