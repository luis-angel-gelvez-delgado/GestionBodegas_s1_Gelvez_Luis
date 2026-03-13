package com.s1.logitrack.controller;

import com.s1.logitrack.dto.request.BodegaRequestDTO;
import com.s1.logitrack.dto.response.BodegaResponseDTO;
import com.s1.logitrack.service.impl.BodegaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@RequiredArgsConstructor
@Tag(name = "Bodegas", description = "Gestion de bodegas del sistema")
public class BodegaController {

    private final BodegaServiceImpl bodegaService;

    @PostMapping
    @Operation(summary = "Crear una nueva bodega")
    public ResponseEntity<BodegaResponseDTO> guardar(
            @Valid @RequestBody BodegaRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bodegaService.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una bodega existente")
    public ResponseEntity<BodegaResponseDTO> actualizar(
            @Valid @RequestBody BodegaRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity
                .ok(bodegaService.actualizar(dto, id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las bodegas")
    public ResponseEntity<List<BodegaResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok(bodegaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bodega por ID")
    public ResponseEntity<BodegaResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(bodegaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una bodega")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {
        bodegaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ubicacion/{ubicacion}")
    @Operation(summary = "Buscar bodegas por ubicacion")
    public ResponseEntity<List<BodegaResponseDTO>> buscarPorUbicacion(
            @PathVariable String ubicacion) {
        return ResponseEntity
                .ok(bodegaService.buscarPorUbicacion(ubicacion));
    }

    @GetMapping("/encargado/{encargado}")
    @Operation(summary = "Buscar bodegas por encargado")
    public ResponseEntity<List<BodegaResponseDTO>> buscarPorEncargado(
            @PathVariable String encargado) {
        return ResponseEntity
                .ok(bodegaService.buscarPorEncargado(encargado));
    }
}