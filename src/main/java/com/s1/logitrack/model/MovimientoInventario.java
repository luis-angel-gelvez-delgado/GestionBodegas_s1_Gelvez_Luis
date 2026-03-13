package com.s1.logitrack.model;

import com.s1.logitrack.config.AuditoriaListener;
import com.s1.logitrack.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movimiento_inventario")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "detalles")
@EntityListeners(AuditoriaListener.class)
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodega_origen_id")
    private Bodega bodegaOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodega_destino_id")
    private Bodega bodegaDestino;

    @OneToMany(mappedBy = "movimiento", cascade = CascadeType.ALL)
    private List<DetalleMovimiento> detalles;
}