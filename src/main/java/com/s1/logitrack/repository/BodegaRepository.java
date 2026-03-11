package com.s1.logitrack.repository;

import com.s1.logitrack.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    List<Bodega> findByUbicacion(String ubicacion);

    List<Bodega> findByEncargado(String encargado);
}