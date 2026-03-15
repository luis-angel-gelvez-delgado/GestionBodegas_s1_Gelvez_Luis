package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.request.DetalleMovimientoRequestDTO;
import com.s1.logitrack.dto.response.MovimientoResponseDTO;
import com.s1.logitrack.dto.request.MovimientoRequestDTO;
import com.s1.logitrack.enums.TipoMovimiento;
import com.s1.logitrack.exception.BusinessRuleException;
import com.s1.logitrack.mapper.MovimientoMapper;
import com.s1.logitrack.model.*;
import com.s1.logitrack.repository.*;
import com.s1.logitrack.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final ProductoRepository productoRepository;
    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final ProductoBodegaRepository productoBodegaRepository;

    @Override
    public MovimientoResponseDTO registrar(MovimientoRequestDTO dto) {

        // 1. Buscar el usuario responsable
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new BusinessRuleException("No existe el usuario con id: " + dto.usuarioId()));

        // 2. Crear el movimiento base
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipo(dto.tipo());
        movimiento.setUsuario(usuario);

        // 3. Validar y asignar bodegas segun tipo
        Bodega bodegaOrigen = null;
        Bodega bodegaDestino = null;

        if (dto.tipo() == TipoMovimiento.ENTRADA) {
            if (dto.bodegaDestinoId() == null) {
                throw new BusinessRuleException("Una ENTRADA requiere bodega destino");
            }
            bodegaDestino = bodegaRepository.findById(dto.bodegaDestinoId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega destino con id: " + dto.bodegaDestinoId()));
            movimiento.setBodegaDestino(bodegaDestino);

        } else if (dto.tipo() == TipoMovimiento.SALIDA) {
            if (dto.bodegaOrigenId() == null) {
                throw new BusinessRuleException("Una SALIDA requiere bodega origen");
            }
            bodegaOrigen = bodegaRepository.findById(dto.bodegaOrigenId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega origen con id: " + dto.bodegaOrigenId()));
            movimiento.setBodegaOrigen(bodegaOrigen);

        } else if (dto.tipo() == TipoMovimiento.TRANSFERENCIA) {
            if (dto.bodegaOrigenId() == null || dto.bodegaDestinoId() == null) {
                throw new BusinessRuleException("Una TRANSFERENCIA requiere bodega origen y destino");
            }
            if (dto.bodegaOrigenId().equals(dto.bodegaDestinoId())) {
                throw new BusinessRuleException("La bodega origen y destino no pueden ser la misma");
            }
            bodegaOrigen = bodegaRepository.findById(dto.bodegaOrigenId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega origen con id: " + dto.bodegaOrigenId()));
            bodegaDestino = bodegaRepository.findById(dto.bodegaDestinoId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega destino con id: " + dto.bodegaDestinoId()));
            movimiento.setBodegaOrigen(bodegaOrigen);
            movimiento.setBodegaDestino(bodegaDestino);
        }

        // 4. Guardar el movimiento
        MovimientoInventario movimientoGuardado = movimientoRepository.save(movimiento);

        // 5. Procesar detalles con stock por bodega
        List<DetalleMovimiento> detalles = new ArrayList<>();

        for (DetalleMovimientoRequestDTO detalleDTO : dto.detalles()) {

            Producto producto = productoRepository.findById(detalleDTO.productoId())
                    .orElseThrow(() -> new BusinessRuleException("No existe el producto con id: " + detalleDTO.productoId()));

            // SALIDA → restar stock en bodega origen
            if (dto.tipo() == TipoMovimiento.SALIDA || dto.tipo() == TipoMovimiento.TRANSFERENCIA) {

                ProductoBodega stockOrigen = productoBodegaRepository
                        .findByProductoIdAndBodegaId(producto.getId(), bodegaOrigen.getId())
                        .orElseThrow(() -> new BusinessRuleException(
                                "El producto " + producto.getNombre() + " no existe en la bodega origen"));

                if (stockOrigen.getStock() < detalleDTO.cantidad()) {
                    throw new BusinessRuleException(
                            "Stock insuficiente para " + producto.getNombre() +
                                    " en bodega origen. Stock disponible: " + stockOrigen.getStock());
                }

                stockOrigen.setStock(stockOrigen.getStock() - detalleDTO.cantidad());
                productoBodegaRepository.save(stockOrigen);

                // Actualizar stock global del producto
                producto.setStock(producto.getStock() - detalleDTO.cantidad());
            }

            // ENTRADA → sumar stock en bodega destino
            if (dto.tipo() == TipoMovimiento.ENTRADA || dto.tipo() == TipoMovimiento.TRANSFERENCIA) {

                // Buscar si ya existe el producto en esa bodega
                ProductoBodega stockDestino = productoBodegaRepository
                        .findByProductoIdAndBodegaId(producto.getId(), bodegaDestino.getId())
                        .orElse(null);


                if (stockDestino == null) {
                    // Si no existe, crear el registro
                    stockDestino = new ProductoBodega();
                    stockDestino.setProducto(producto);
                    stockDestino.setBodega(bodegaDestino);
                    stockDestino.setStock(0);
                }

                stockDestino.setStock(stockDestino.getStock() + detalleDTO.cantidad());
                productoBodegaRepository.save(stockDestino);


                // Actualizar stock global del producto
                producto.setStock(producto.getStock() + detalleDTO.cantidad());
            }

            // Guardar producto con stock global actualizado
            productoRepository.save(producto);

            // Crear detalle
            DetalleMovimiento detalle = new DetalleMovimiento();
            detalle.setCantidad(detalleDTO.cantidad());
            detalle.setProducto(producto);
            detalle.setMovimiento(movimientoGuardado);
            detalles.add(detalle);
        }

        // 6. Guardar todos los detalles
        detalleMovimientoRepository.saveAll(detalles);
        movimientoGuardado.setDetalles(detalles);

        return movimientoMapper.entidadADTO(movimientoGuardado);
    }

    @Override
    public List<MovimientoResponseDTO> listarTodos() {
        return movimientoRepository.findAll()
                .stream()
                .map(movimientoMapper::entidadADTO)
                .toList();
    }

    @Override
    public MovimientoResponseDTO buscarPorId(Long id) {
        MovimientoInventario m = movimientoRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("No existe el movimiento con id: " + id));
        return movimientoMapper.entidadADTO(m);
    }

    @Override
    public List<MovimientoResponseDTO> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return movimientoRepository.findByFechaBetween(inicio, fin)
                .stream()
                .map(movimientoMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<MovimientoResponseDTO> buscarPorTipo(TipoMovimiento tipo) {
        return movimientoRepository.findByTipo(tipo)
                .stream()
                .map(movimientoMapper::entidadADTO)
                .toList();
    }

    @Override
    public List<MovimientoResponseDTO> buscarPorUsuario(Long usuarioId) {
        return movimientoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(movimientoMapper::entidadADTO)
                .toList();
    }
}