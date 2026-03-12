package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.request.UsuarioRequestDTO;
import com.s1.logitrack.dto.response.UsuarioResponseDTO;
import com.s1.logitrack.enums.TipoRol;
import com.s1.logitrack.exception.BusinessRuleException;
import com.s1.logitrack.mapper.UsuarioMapper;
import com.s1.logitrack.model.Usuario;
import com.s1.logitrack.repository.UsuarioRepository;
import com.s1.logitrack.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {
        // Verificar que el username no exista
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new BusinessRuleException("Ya existe un usuario con el username: " + dto.username());
        }
        Usuario u = usuarioMapper.DTOaEntidad(dto);
        Usuario guardado = usuarioRepository.save(u);
        return usuarioMapper.entidadADTO(guardado);
    }

    @Override
    public UsuarioResponseDTO actualizar(UsuarioRequestDTO dto, Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el usuario con id: " + id));
        usuarioMapper.actualizarEntidadDesdeDTO(u, dto);
        Usuario actualizado = usuarioRepository.save(u);
        return usuarioMapper.entidadADTO(actualizado);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::entidadADTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el usuario con id: " + id));
        return usuarioMapper.entidadADTO(u);
    }

    @Override
    public void eliminar(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el usuario con id: " + id));
        usuarioRepository.delete(u);
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorRol(TipoRol rol) {
        return usuarioRepository.findByRol(rol)
                .stream()
                .map(usuarioMapper::entidadADTO)
                .toList();
    }
}