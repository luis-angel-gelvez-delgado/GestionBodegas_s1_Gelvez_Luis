package com.s1.logitrack.mapper;

import com.s1.logitrack.dto.request.ProductoRequestDTO;
import com.s1.logitrack.dto.response.ProductoResponseDTO;
import com.s1.logitrack.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponseDTO entidadADTO(Producto producto) {
        if (producto == null) return null;
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getStock(),
                producto.getPrecio()
        );
    }

    public Producto DTOaEntidad(ProductoRequestDTO dto) {
        if (dto == null) return null;
        Producto p = new Producto();
        p.setNombre(dto.nombre());
        p.setCategoria(dto.categoria());
        p.setStock(dto.stock());
        p.setPrecio(dto.precio());
        return p;
    }

    public void actualizarEntidadDesdeDTO(Producto producto, ProductoRequestDTO dto) {
        if (producto == null || dto == null) return;
        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());
        producto.setStock(dto.stock());
        producto.setPrecio(dto.precio());
    }
}