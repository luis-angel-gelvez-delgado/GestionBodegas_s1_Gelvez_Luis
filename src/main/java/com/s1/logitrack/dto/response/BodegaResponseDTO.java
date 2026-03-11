package com.s1.logitrack.dto.response;

public record BodegaResponseDTO(
        Long id,
        String nombre,
        String ubicacion,
        Integer capacidad,
        String encargado
) {}