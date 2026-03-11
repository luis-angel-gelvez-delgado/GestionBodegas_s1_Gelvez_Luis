package com.s1.logitrack.repository;

import com.s1.logitrack.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByStockLessThan(Integer stock);

    List<Producto> findByCategoria(String categoria);
}