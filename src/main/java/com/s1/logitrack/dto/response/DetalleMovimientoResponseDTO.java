package com.s1.logitrack.dto.response;

public record DetalleMovimientoResponseDTO(
        Long id,
        Integer cantidad,
        ProductoResponseDTO producto
) {}