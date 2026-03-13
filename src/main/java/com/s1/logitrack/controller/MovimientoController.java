package com.s1.logitrack.controller;

import com.s1.logitrack.dto.request.MovimientoRequestDTO;
import com.s1.logitrack.dto.response.MovimientoResponseDTO;
import com.s1.logitrack.enums.TipoMovimiento;
import com.s1.logitrack.service.impl.MovimientoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "Gestion de movimientos de inventario")
public class MovimientoController {

    private final MovimientoServiceImpl movimientoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo movimiento de inventario")
    public ResponseEntity<MovimientoResponseDTO> registrar(
            @Valid @RequestBody MovimientoRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movimientoService.registrar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public ResponseEntity<List<MovimientoResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok(movimientoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar movimiento por ID")
    public ResponseEntity<MovimientoResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(movimientoService.buscarPorId(id));
    }

    @GetMapping("/fechas")
    @Operation(summary = "Buscar movimientos por rango de fechas")
    public ResponseEntity<List<MovimientoResponseDTO>> buscarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity
                .ok(movimientoService.buscarPorRangoFechas(inicio, fin));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar movimientos por tipo")
    public ResponseEntity<List<MovimientoResponseDTO>> buscarPorTipo(
            @PathVariable TipoMovimiento tipo) {
        return ResponseEntity
                .ok(movimientoService.buscarPorTipo(tipo));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar movimientos por usuario")
    public ResponseEntity<List<MovimientoResponseDTO>> buscarPorUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity
                .ok(movimientoService.buscarPorUsuario(usuarioId));
    }
}