package com.s1.logitrack.service;

import com.s1.logitrack.dto.request.ProductoRequestDTO;
import com.s1.logitrack.dto.response.ProductoResponseDTO;

import java.util.List;


public interface ProductoService {
    ProductoResponseDTO guardar(ProductoRequestDTO dto);
    ProductoResponseDTO actualizar(ProductoRequestDTO dto, Long id);
    List<ProductoResponseDTO> listarTodos();
    ProductoResponseDTO buscarPorId(Long id);
    void eliminar(Long id);





    List<ProductoResponseDTO> buscarStockBajo(Integer limite);
    List<ProductoResponseDTO> buscarPorCategoria(String categoria);
}
