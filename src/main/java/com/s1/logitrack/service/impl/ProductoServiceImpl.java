package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.request.ProductoRequestDTO;
import com.s1.logitrack.dto.response.ProductoResponseDTO;
import com.s1.logitrack.exception.BusinessRuleException;
import com.s1.logitrack.mapper.ProductoMapper;
import com.s1.logitrack.model.Producto;
import com.s1.logitrack.repository.ProductoRepository;
import com.s1.logitrack.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        Producto p = productoMapper.DTOaEntidad(dto);
        Producto guardado = productoRepository.save(p);
        return productoMapper.entidadADTO(guardado);
    }

    @Override
    public ProductoResponseDTO actualizar(ProductoRequestDTO dto, Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el producto con id: " + id));
        productoMapper.actualizarEntidadDesdeDTO(p, dto);
        Producto actualizado = productoRepository.save(p);
        return productoMapper.entidadADTO(actualizado);
    }

    @Override
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::entidadADTO)
                .toList();
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el producto con id: " + id));
        return productoMapper.entidadADTO(p);
    }

    @Override
    public void eliminar(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el producto con id: " + id));
        productoRepository.delete(p);
    }

    @Override
    public List<ProductoResponseDTO> buscarStockBajo(Integer limite) {
        return productoRepository.findByStockLessThan(limite)
                .stream()
                .map(productoMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria)
                .stream()
                .map(productoMapper::entidadADTO)
                .toList();
    }
}