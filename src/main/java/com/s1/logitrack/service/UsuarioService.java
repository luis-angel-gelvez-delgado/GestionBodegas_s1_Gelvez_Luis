package com.s1.logitrack.service;

import com.s1.logitrack.dto.request.UsuarioRequestDTO;
import com.s1.logitrack.dto.response.UsuarioResponseDTO;
import com.s1.logitrack.enums.TipoRol;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO guardar(UsuarioRequestDTO dto);
    UsuarioResponseDTO actualizar(UsuarioRequestDTO dto, Long id);
    List<UsuarioResponseDTO> listarTodos();
    UsuarioResponseDTO buscarPorId(Long id);
    void eliminar(Long id);

    List<UsuarioResponseDTO> buscarPorRol(TipoRol rol);
}
