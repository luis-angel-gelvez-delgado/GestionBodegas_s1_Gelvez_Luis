package com.s1.logitrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bodega")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private String encargado;
}