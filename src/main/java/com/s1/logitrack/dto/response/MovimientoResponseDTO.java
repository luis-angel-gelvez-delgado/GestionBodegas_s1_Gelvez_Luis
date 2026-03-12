package com.s1.logitrack.dto.response;

import com.s1.logitrack.enums.TipoMovimiento;
import java.time.LocalDateTime;
import java.util.List;

public record MovimientoResponseDTO(
        Long id,
        LocalDateTime fecha,
        TipoMovimiento tipo,
        UsuarioResponseDTO usuario,
        BodegaResponseDTO bodegaOrigen,
        BodegaResponseDTO bodegaDestino,
        List<DetalleMovimientoResponseDTO> detalles
) {}