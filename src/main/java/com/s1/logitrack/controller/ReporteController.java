package com.s1.logitrack.controller;

import com.s1.logitrack.dto.response.ReporteResumenDTO;
import com.s1.logitrack.service.impl.ReporteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Reportes y resúmenes del sistema")
public class ReporteController {

    private final ReporteServiceImpl reporteService;

    @GetMapping("/resumen")
    @Operation(summary = "Reporte general: stock por cada bodega y los productos mas movidos")
    public ResponseEntity<ReporteResumenDTO> resumenGeneral() {
        return ResponseEntity.ok(reporteService.generarResumen());
    }
}