package com.s1.logitrack.dto.response;

import java.time.LocalDateTime;

public record AuditoriaResponseDTO(
        Long id,
        String tipoOperacion,
        LocalDateTime fecha,
        String usuario,
        String entidadAfectada,
        String valorAnterior,
        String valorNuevo
) {}
