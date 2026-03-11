package com.s1.logitrack.repository;

import com.s1.logitrack.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    List<Auditoria> findByUsuario(String usuario);

    List<Auditoria> findByTipoOperacion(String tipoOperacion);

    List<Auditoria> findByEntidadAfectada(String entidadAfectada);
}