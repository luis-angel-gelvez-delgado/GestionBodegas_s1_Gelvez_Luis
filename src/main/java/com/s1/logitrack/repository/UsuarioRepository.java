package com.s1.logitrack.repository;

import com.s1.logitrack.model.TipoRol;
import com.s1.logitrack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByRol(TipoRol rol);

    boolean existsByUsername(String username);
}