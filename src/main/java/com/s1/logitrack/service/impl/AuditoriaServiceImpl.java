package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.response.AuditoriaResponseDTO;
import com.s1.logitrack.mapper.AuditoriaMapper;
import com.s1.logitrack.model.Auditoria;
import com.s1.logitrack.repository.AuditoriaRepository;
import com.s1.logitrack.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final AuditoriaMapper auditoriaMapper;

    @Override
    public List<AuditoriaResponseDTO> listarTodos() {
        return auditoriaRepository.findAll()
                .stream()
                .map(auditoriaMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<AuditoriaResponseDTO> buscarPorUsuario(String usuario) {
        return auditoriaRepository.findByUsuario(usuario)
                .stream()
                .map(auditoriaMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<AuditoriaResponseDTO> buscarPorTipoOperacion(String tipoOperacion) {
        return auditoriaRepository.findByTipoOperacion(tipoOperacion)
                .stream()
                .map(auditoriaMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<AuditoriaResponseDTO> buscarPorEntidad(String entidadAfectada) {
        return auditoriaRepository.findByEntidadAfectada(entidadAfectada)
                .stream()
                .map(auditoriaMapper::entidadADTO)
                .toList();
    }
}