package com.s1.logitrack.dto.response;

import java.util.List;

public record ReporteResumenDTO(
        List<StockPorBodegaDTO> stockPorBodega,
        List<ProductoMasMovidoDTO> productosMasMovidos
) {}