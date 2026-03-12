package com.s1.logitrack.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String mensaje,
        LocalDateTime fecha
) {}