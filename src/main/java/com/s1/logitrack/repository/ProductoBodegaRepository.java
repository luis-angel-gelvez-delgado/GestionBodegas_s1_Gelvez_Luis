package com.s1.logitrack.repository;

import com.s1.logitrack.model.ProductoBodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoBodegaRepository extends JpaRepository<ProductoBodega, Long> {

    Optional<ProductoBodega> findByProductoIdAndBodegaId(Long productoId, Long bodegaId);

    List<ProductoBodega> findByProductoId(Long productoId);

    List<ProductoBodega> findByBodegaId(Long bodegaId);


}
