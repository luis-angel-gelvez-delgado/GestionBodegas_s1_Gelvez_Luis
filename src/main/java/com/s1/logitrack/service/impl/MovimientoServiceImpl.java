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

        // 3. Validar y asignar bodegas segun el tipo de movimiento
        if (dto.tipo() == TipoMovimiento.ENTRADA) {
            // ENTRADA: solo necesita bodega destino
            if (dto.bodegaDestinoId() == null) {
                throw new BusinessRuleException("Una ENTRADA requiere bodega destino");
            }
            Bodega destino = bodegaRepository.findById(dto.bodegaDestinoId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega destino con id: " + dto.bodegaDestinoId()));
            movimiento.setBodegaDestino(destino);

        } else if (dto.tipo() == TipoMovimiento.SALIDA) {
            // SALIDA: solo necesita bodega origen
            if (dto.bodegaOrigenId() == null) {
                throw new BusinessRuleException("Una SALIDA requiere bodega origen");
            }
            Bodega origen = bodegaRepository.findById(dto.bodegaOrigenId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega origen con id: " + dto.bodegaOrigenId()));
            movimiento.setBodegaOrigen(origen);

        } else if (dto.tipo() == TipoMovimiento.TRANSFERENCIA) {
            // TRANSFERENCIA: necesita ambas bodegas
            if (dto.bodegaOrigenId() == null || dto.bodegaDestinoId() == null) {
                throw new BusinessRuleException("Una TRANSFERENCIA requiere bodega origen y destino");
            }
            if (dto.bodegaOrigenId().equals(dto.bodegaDestinoId())) {
                throw new BusinessRuleException("La bodega origen y destino no pueden ser la misma");
            }
            Bodega origen = bodegaRepository.findById(dto.bodegaOrigenId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega origen con id: " + dto.bodegaOrigenId()));
            Bodega destino = bodegaRepository.findById(dto.bodegaDestinoId())
                    .orElseThrow(() -> new BusinessRuleException("No existe la bodega destino con id: " + dto.bodegaDestinoId()));
            movimiento.setBodegaOrigen(origen);
            movimiento.setBodegaDestino(destino);
        }

        // 4. Guardar el movimiento para obtener su id
        MovimientoInventario movimientoGuardado = movimientoRepository.save(movimiento);

        // 5. Procesar los detalles (productos y cantidades)
        List<DetalleMovimiento> detalles = new ArrayList<>();

        for (DetalleMovimientoRequestDTO detalleDTO : dto.detalles()) {

            Producto producto = productoRepository.findById(detalleDTO.productoId())
                    .orElseThrow(() -> new BusinessRuleException("No existe el producto con id: " + detalleDTO.productoId()));

            // Validar stock disponible en SALIDA y TRANSFERENCIA
            if (dto.tipo() == TipoMovimiento.SALIDA || dto.tipo() == TipoMovimiento.TRANSFERENCIA) {
                if (producto.getStock() < detalleDTO.cantidad()) {
                    throw new BusinessRuleException("Stock insuficiente para el producto: " + producto.getNombre()
                            + ". Stock disponible: " + producto.getStock());
                }
                // Reducir stock
                producto.setStock(producto.getStock() - detalleDTO.cantidad());
            }

            // Aumentar stock en ENTRADA y TRANSFERENCIA
            if (dto.tipo() == TipoMovimiento.ENTRADA || dto.tipo() == TipoMovimiento.TRANSFERENCIA) {
                producto.setStock(producto.getStock() + detalleDTO.cantidad());
            }

            // Guardar producto con stock actualizado
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