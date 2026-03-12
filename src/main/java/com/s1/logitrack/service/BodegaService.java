package com.s1.logitrack.service;

import com.s1.logitrack.dto.request.BodegaRequestDTO;
import com.s1.logitrack.dto.response.BodegaResponseDTO;
import java.util.List;
public interface BodegaService {

    BodegaResponseDTO guardar(BodegaRequestDTO dto);
    BodegaResponseDTO actualizar(BodegaRequestDTO dto, Long id);
    List<BodegaResponseDTO> listarTodos();
    BodegaResponseDTO buscarPorId(Long id);
    void eliminar(Long id);





    List<BodegaResponseDTO> buscarPorUbicacion(String ubicacion);
    List<BodegaResponseDTO> buscarPorEncargado(String encargado);
}