package com.s1.logitrack.controller;

import com.s1.logitrack.dto.request.UsuarioRequestDTO;
import com.s1.logitrack.dto.response.UsuarioResponseDTO;
import com.s1.logitrack.enums.TipoRol;
import com.s1.logitrack.service.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestion de usuarios del sistema")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<UsuarioResponseDTO> guardar(
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @Valid @RequestBody UsuarioRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity
                .ok(usuarioService.actualizar(dto, id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity
                .ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @PathVariable Long id) {
        return ResponseEntity
                .ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {
        usuarioService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/rol/{rol}")
    @Operation(summary = "Buscar usuarios por rol")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorRol(
            @PathVariable TipoRol rol) {
        return ResponseEntity
                .ok(usuarioService.buscarPorRol(rol));
    }
}