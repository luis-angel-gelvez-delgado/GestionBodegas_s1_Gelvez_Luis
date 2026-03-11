package com.s1.logitrack.mapper;

import com.s1.logitrack.dto.response.DetalleMovimientoResponseDTO;
import com.s1.logitrack.model.DetalleMovimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleMovimientoMapper {

    private final ProductoMapper productoMapper;

    public DetalleMovimientoResponseDTO entidadADTO(DetalleMovimiento detalle) {
        if (detalle == null) return null;
        return new DetalleMovimientoResponseDTO(
                detalle.getId(),
                detalle.getCantidad(),
                productoMapper.entidadADTO(detalle.getProducto())
        );
    }
}