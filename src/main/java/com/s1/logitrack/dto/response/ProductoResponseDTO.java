package com.s1.logitrack.dto.response;

import java.math.BigDecimal;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String categoria,
        Integer stock,
        BigDecimal precio
) {}