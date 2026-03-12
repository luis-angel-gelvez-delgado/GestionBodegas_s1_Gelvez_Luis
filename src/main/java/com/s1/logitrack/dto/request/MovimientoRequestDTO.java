package com.s1.logitrack.dto.request;

import com.s1.logitrack.enums.TipoMovimiento;
import jakarta.validation.constraints.*;
import java.util.List;

public record MovimientoRequestDTO(

        @NotNull(message = "El tipo de movimiento no puede ser nulo")
        TipoMovimiento tipo,

        @NotNull(message = "El usuario no puede ser nulo")
        Long usuarioId,

        Long bodegaOrigenId,

        Long bodegaDestinoId,

        @NotNull(message = "Los detalles no pueden ser nulos")
        @Size(min = 1, message = "Debe haber al menos un detalle")
        List<DetalleMovimientoRequestDTO> detalles
) {}