package com.s1.logitrack.controller;

import com.s1.logitrack.dto.request.ProductoRequestDTO;
import com.s1.logitrack.dto.response.ProductoResponseDTO;
import com.s1.logitrack.service.impl.ProductoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestion de productos del sistema")
public class ProductoController {

    private final ProductoServiceImpl productoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ProductoResponseDTO> guardar(
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @Valid @RequestBody ProductoRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity
                .ok(productoService.actualizar(dto, id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID")
    public ResponseEntity<ProductoResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(productoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {
        productoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/stock-bajo")
    @Operation(summary = "Productos con stock bajo (menos de 10 unidades)")
    public ResponseEntity<List<ProductoResponseDTO>> stockBajo(
            @RequestParam(defaultValue = "10") Integer limite) {
        return ResponseEntity
                .ok(productoService.buscarStockBajo(limite));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar productos por categoria")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorCategoria(
            @PathVariable String categoria) {
        return ResponseEntity
                .ok(productoService.buscarPorCategoria(categoria));
    }
}