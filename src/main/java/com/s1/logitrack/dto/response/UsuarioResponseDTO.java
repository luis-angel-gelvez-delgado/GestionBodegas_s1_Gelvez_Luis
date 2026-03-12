package com.s1.logitrack.dto.response;

import com.s1.logitrack.enums.TipoRol;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String username,
        TipoRol rol
) {}