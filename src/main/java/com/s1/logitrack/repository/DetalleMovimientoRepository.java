package com.s1.logitrack.repository;

import com.s1.logitrack.model.DetalleMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {

    List<DetalleMovimiento> findByMovimientoId(Long movimientoId);

    List<DetalleMovimiento> findByProductoId(Long productoId);
}