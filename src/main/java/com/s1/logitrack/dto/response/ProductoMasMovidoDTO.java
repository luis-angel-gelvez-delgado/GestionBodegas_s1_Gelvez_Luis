package com.s1.logitrack.dto.response;

public record ProductoMasMovidoDTO(
        Long productoId,
        String nombreProducto,
        String categoria,
        Integer totalMovido
) {}