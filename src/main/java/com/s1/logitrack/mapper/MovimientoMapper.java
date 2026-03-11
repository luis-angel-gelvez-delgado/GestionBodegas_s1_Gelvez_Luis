package com.s1.logitrack.mapper;

import com.s1.logitrack.dto.response.MovimientoResponseDTO;
import com.s1.logitrack.model.MovimientoInventario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovimientoMapper {

    private final UsuarioMapper usuarioMapper;
    private final BodegaMapper bodegaMapper;
    private final DetalleMovimientoMapper detalleMovimientoMapper;

    public MovimientoResponseDTO entidadADTO(MovimientoInventario movimiento) {
        if (movimiento == null) return null;
        return new MovimientoResponseDTO(
                movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipo(),
                usuarioMapper.entidadADTO(movimiento.getUsuario()),
                bodegaMapper.entidadADTO(movimiento.getBodegaOrigen()),
                bodegaMapper.entidadADTO(movimiento.getBodegaDestino()),
                movimiento.getDetalles() == null ? List.of() :
                        movimiento.getDetalles().stream()
                                .map(detalleMovimientoMapper::entidadADTO)
                                .toList()
        );
    }
}