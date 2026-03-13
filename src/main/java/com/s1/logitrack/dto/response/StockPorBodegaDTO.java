package com.s1.logitrack.dto.response;

public record StockPorBodegaDTO(
        Long bodegaId,
        String nombreBodega,
        String ubicacion,
        Integer totalProductos,
        Integer totalStock
) {}