package com.s1.logitrack.model;

import com.s1.logitrack.config.AuditoriaListener;
import com.s1.logitrack.enums.TipoRol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "password")
@EntityListeners(AuditoriaListener.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRol rol;
}