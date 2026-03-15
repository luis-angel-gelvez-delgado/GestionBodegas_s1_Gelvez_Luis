package com.s1.logitrack.auth;

import com.s1.logitrack.enums.TipoRol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank String nombre,
        @NotBlank String username,
        @NotBlank String password,
        @NotNull TipoRol rol
) {}