package com.s1.logitrack.repository;

import com.s1.logitrack.model.MovimientoInventario;
import com.s1.logitrack.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoInventario, Long> {

    List<MovimientoInventario> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<MovimientoInventario> findByTipo(TipoMovimiento tipo);

    List<MovimientoInventario> findByUsuarioId(Long usuarioId);

    List<MovimientoInventario> findByBodegaOrigenId(Long bodegaId);
}