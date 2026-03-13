package com.s1.logitrack.controller;

import com.s1.logitrack.dto.response.AuditoriaResponseDTO;
import com.s1.logitrack.service.impl.AuditoriaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias")
@RequiredArgsConstructor
@Tag(name = "Auditorias", description = "Consulta de auditorias del sistema")
public class AuditoriaController {

    private final AuditoriaServiceImpl auditoriaService;

    @GetMapping
    @Operation(summary = "Listar todas las auditorias")
    public ResponseEntity<List<AuditoriaResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok(auditoriaService.listarTodos());
    }

    @GetMapping("/usuario/{usuario}")
    @Operation(summary = "Buscar auditorias por usuario")
    public ResponseEntity<List<AuditoriaResponseDTO>> buscarPorUsuario(
            @PathVariable String usuario) {
        return ResponseEntity
                .ok(auditoriaService.buscarPorUsuario(usuario));
    }

    @GetMapping("/operacion/{tipoOperacion}")
    @Operation(summary = "Buscar auditorias por tipo de operacion")
    public ResponseEntity<List<AuditoriaResponseDTO>> buscarPorTipoOperacion(
            @PathVariable String tipoOperacion) {
        return ResponseEntity
                .ok(auditoriaService.buscarPorTipoOperacion(tipoOperacion));
    }

    @GetMapping("/entidad/{entidadAfectada}")
    @Operation(summary = "Buscar auditorias por entidad afectada")
    public ResponseEntity<List<AuditoriaResponseDTO>> buscarPorEntidad(
            @PathVariable String entidadAfectada) {
        return ResponseEntity
                .ok(auditoriaService.buscarPorEntidad(entidadAfectada));
    }
}
