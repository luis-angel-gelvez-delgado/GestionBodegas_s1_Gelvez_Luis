package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.response.*;
import com.s1.logitrack.model.*;
import com.s1.logitrack.repository.*;
import com.s1.logitrack.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final BodegaRepository bodegaRepository;
    private final ProductoRepository productoRepository;
    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final ProductoBodegaRepository productoBodegaRepository;

    @Override
    public ReporteResumenDTO generarResumen() {

        // 1. Stock real por bodega usando ProductoBodega
        List<Bodega> bodegas = bodegaRepository.findAll();

        List<StockPorBodegaDTO> stockPorBodega = bodegas.stream()
                .map(b -> {
                    List<ProductoBodega> stocksEnBodega = productoBodegaRepository.findByBodegaId(b.getId());

                    Integer totalProductos = stocksEnBodega.size();
                    Integer totalStock = stocksEnBodega.stream()
                            .mapToInt(ProductoBodega::getStock)
                            .sum();

                    return new StockPorBodegaDTO(
                            b.getId(),
                            b.getNombre(),
                            b.getUbicacion(),
                            totalProductos,
                            totalStock
                    );
                })
                .toList();

        // 2. Productos mas movidos
        List<DetalleMovimiento> detalles = detalleMovimientoRepository.findAll();

        Map<Producto, Integer> movimientosPorProducto = new HashMap<>();

        for (DetalleMovimiento detalle : detalles) {
            Producto producto = detalle.getProducto();
            movimientosPorProducto.merge(producto, detalle.getCantidad(), Integer::sum);
        }

        List<ProductoMasMovidoDTO> productosMasMovidos = movimientosPorProducto
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Producto, Integer>comparingByValue().reversed())
                .map(e -> new ProductoMasMovidoDTO(
                        e.getKey().getId(),
                        e.getKey().getNombre(),
                        e.getKey().getCategoria(),
                        e.getValue()
                ))
                .toList();

        return new ReporteResumenDTO(stockPorBodega, productosMasMovidos);
    }
}