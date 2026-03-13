package com.s1.logitrack.config;

import com.s1.logitrack.model.Auditoria;
import com.s1.logitrack.repository.AuditoriaRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditoriaListener {

    private static AuditoriaRepository auditoriaRepository;

    @Autowired
    public void setAuditoriaRepository(AuditoriaRepository repo) {
        AuditoriaListener.auditoriaRepository = repo;
    }

    @PostPersist
    public void despuesDeGuardar(Object entidad) {
        registrar("INSERT", entidad);
    }

    @PostUpdate
    public void despuesDeActualizar(Object entidad) {
        registrar("UPDATE", entidad);
    }

    @PostRemove
    public void despuesDeEliminar(Object entidad) {
        registrar("DELETE", entidad);
    }

    private void registrar(String tipoOperacion, Object entidad) {
        if (entidad instanceof Auditoria) return;

        if (auditoriaRepository == null) return;

        Auditoria auditoria = new Auditoria();
        auditoria.setTipoOperacion(tipoOperacion);
        auditoria.setFecha(LocalDateTime.now());
        auditoria.setUsuario("sistema");
        auditoria.setEntidadAfectada(entidad.getClass().getSimpleName());
        auditoria.setValorAnterior(null);
        auditoria.setValorNuevo(entidad.toString());

        auditoriaRepository.save(auditoria);
    }
}