package com.s1.logitrack.service;

import com.s1.logitrack.dto.request.MovimientoRequestDTO;
import com.s1.logitrack.dto.response.MovimientoResponseDTO;
import com.s1.logitrack.enums.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService {
    MovimientoResponseDTO registrar(MovimientoRequestDTO dto);
    List<MovimientoResponseDTO> listarTodos();
    MovimientoResponseDTO buscarPorId(Long id);

    List<MovimientoResponseDTO> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);
    List<MovimientoResponseDTO> buscarPorTipo(TipoMovimiento tipo);
    List<MovimientoResponseDTO> buscarPorUsuario(Long usuarioId);
}
