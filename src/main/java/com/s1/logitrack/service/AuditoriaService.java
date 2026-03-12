package com.s1.logitrack.service;

import com.s1.logitrack.dto.response.AuditoriaResponseDTO;
import java.util.List;

public interface AuditoriaService {

    List<AuditoriaResponseDTO> listarTodos();
    List<AuditoriaResponseDTO> buscarPorUsuario(String usuario);
    List<AuditoriaResponseDTO> buscarPorTipoOperacion(String tipoOperacion);
    List<AuditoriaResponseDTO> buscarPorEntidad(String entidadAfectada);

}
