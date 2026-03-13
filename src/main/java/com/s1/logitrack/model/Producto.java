package com.s1.logitrack.model;

import com.s1.logitrack.config.AuditoriaListener;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@EntityListeners(AuditoriaListener.class)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private BigDecimal precio;
}