package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.request.BodegaRequestDTO;
import com.s1.logitrack.dto.response.BodegaResponseDTO;
import com.s1.logitrack.exception.BusinessRuleException;
import com.s1.logitrack.mapper.BodegaMapper;
import com.s1.logitrack.model.Bodega;
import com.s1.logitrack.repository.BodegaRepository;
import com.s1.logitrack.service.BodegaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodegaServiceImpl implements BodegaService {

    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    @Override
    public BodegaResponseDTO guardar(BodegaRequestDTO dto) {
        Bodega b = bodegaMapper.DTOaEntidad(dto);
        Bodega guardada = bodegaRepository.save(b);
        return bodegaMapper.entidadADTO(guardada);
    }

    @Override
    public BodegaResponseDTO actualizar(BodegaRequestDTO dto, Long id) {
        Bodega b = bodegaRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe la bodega con id: " + id));
        bodegaMapper.actualizarEntidadDesdeDTO(b, dto);
        Bodega actualizada = bodegaRepository.save(b);
        return bodegaMapper.entidadADTO(actualizada);
    }

    @Override
    public List<BodegaResponseDTO> listarTodos() {
        return bodegaRepository.findAll()
                .stream()
                .map(bodegaMapper::entidadADTO)
                .toList();
    }

    @Override
    public BodegaResponseDTO buscarPorId(Long id) {
        Bodega b = bodegaRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe la bodega con id: " + id));
        return bodegaMapper.entidadADTO(b);
    }

    @Override
    public void eliminar(Long id) {
        Bodega b = bodegaRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe la bodega con id: " + id));
        bodegaRepository.delete(b);
    }

    @Override
    public List<BodegaResponseDTO> buscarPorUbicacion(String ubicacion) {
        return bodegaRepository.findByUbicacion(ubicacion)
                .stream()
                .map(bodegaMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<BodegaResponseDTO> buscarPorEncargado(String encargado) {
        return bodegaRepository.findByEncargado(encargado)
                .stream()
                .map(bodegaMapper::entidadADTO)
                .toList();
    }
}