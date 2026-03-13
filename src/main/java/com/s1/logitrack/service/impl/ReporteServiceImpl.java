package com.s1.logitrack.service.impl;

import com.s1.logitrack.dto.response.*;
import com.s1.logitrack.model.*;
import com.s1.logitrack.repository.*;
import com.s1.logitrack.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final BodegaRepository bodegaRepository;
    private final ProductoRepository productoRepository;
    private final DetalleMovimientoRepository detalleMovimientoRepository;

    @Override
    public ReporteResumenDTO generarResumen() {

        // 1. Stock por bodega
        List<Bodega> bodegas = bodegaRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        // Calculamos el total de productos y stock general
        // (en este modelo el stock es global por producto, no por bodega)
        List<StockPorBodegaDTO> stockPorBodega = bodegas.stream()
                .map(b -> new StockPorBodegaDTO(
                        b.getId(),
                        b.getNombre(),
                        b.getUbicacion(),
                        productos.size(),
                        productos.stream()
                                .mapToInt(Producto::getStock)
                                .sum()
                ))
                .toList();

        // 2. Productos mas movidos
        // Agrupamos los detalles por producto y sumamos cantidades
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