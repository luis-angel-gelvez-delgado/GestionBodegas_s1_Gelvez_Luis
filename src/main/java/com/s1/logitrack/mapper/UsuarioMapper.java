package com.s1.logitrack.mapper;

import com.s1.logitrack.dto.request.UsuarioRequestDTO;
import com.s1.logitrack.dto.response.UsuarioResponseDTO;
import com.s1.logitrack.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO entidadADTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getRol()
        );
    }

    public Usuario DTOaEntidad(UsuarioRequestDTO dto) {
        if (dto == null) return null;
        Usuario u = new Usuario();
        u.setNombre(dto.nombre());
        u.setUsername(dto.username());
        u.setPassword(dto.password());
        u.setRol(dto.rol());
        return u;
    }

    public void actualizarEntidadDesdeDTO(Usuario usuario, UsuarioRequestDTO dto) {
        if (usuario == null || dto == null) return;
        usuario.setNombre(dto.nombre());
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setRol(dto.rol());
    }
}