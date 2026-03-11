package com.s1.logitrack.mapper;

import com.s1.logitrack.dto.response.AuditoriaResponseDTO;
import com.s1.logitrack.model.Auditoria;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaMapper {

    public AuditoriaResponseDTO entidadADTO(Auditoria auditoria) {
        if (auditoria == null) return null;
        return new AuditoriaResponseDTO(
                auditoria.getId(),
                auditoria.getTipoOperacion(),
                auditoria.getFecha(),
                auditoria.getUsuario(),
                auditoria.getEntidadAfectada(),
                auditoria.getValorAnterior(),
                auditoria.getValorNuevo()
        );
    }
}