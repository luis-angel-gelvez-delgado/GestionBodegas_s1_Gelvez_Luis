package com.s1.logitrack.mapper;

import com.s1.logitrack.dto.request.BodegaRequestDTO;
import com.s1.logitrack.dto.response.BodegaResponseDTO;
import com.s1.logitrack.model.Bodega;
import org.springframework.stereotype.Component;

@Component
public class BodegaMapper {

    public BodegaResponseDTO entidadADTO(Bodega bodega) {
        if (bodega == null) return null;
        return new BodegaResponseDTO(
                bodega.getId(),
                bodega.getNombre(),
                bodega.getUbicacion(),
                bodega.getCapacidad(),
                bodega.getEncargado()
        );
    }

    public Bodega DTOaEntidad(BodegaRequestDTO dto) {
        if (dto == null) return null;
        Bodega b = new Bodega();
        b.setNombre(dto.nombre());
        b.setUbicacion(dto.ubicacion());
        b.setCapacidad(dto.capacidad());
        b.setEncargado(dto.encargado());
        return b;
    }

    public void actualizarEntidadDesdeDTO(Bodega bodega, BodegaRequestDTO dto) {
        if (bodega == null || dto == null) return;
        bodega.setNombre(dto.nombre());
        bodega.setUbicacion(dto.ubicacion());
        bodega.setCapacidad(dto.capacidad());
        bodega.setEncargado(dto.encargado());
    }
}