package com.s1.logitrack.auth;

import com.s1.logitrack.config.JwtService;
import com.s1.logitrack.exception.BusinessRuleException;
import com.s1.logitrack.model.Usuario;
import com.s1.logitrack.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Registro y login de usuarios")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        if (usuarioRepository.existsByUsername(request.username())) {
            throw new BusinessRuleException("Ya existe un usuario con el username: " + request.username());
        }

        Usuario u = new Usuario();
        u.setNombre(request.nombre());
        u.setUsername(request.username());
        // Encriptamos la password con BCrypt
        u.setPassword(passwordEncoder.encode(request.password()));
        u.setRol(request.rol());

        usuarioRepository.save(u);

        String token = jwtService.generateToken(
                u.getUsername(),
                Map.of("rol", u.getRol().name())
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(token));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        Usuario u = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessRuleException("Credenciales invalidas"));

        // Verificar password encriptada
        if (!passwordEncoder.matches(request.password(), u.getPassword())) {
            throw new BusinessRuleException("Credenciales invalidas");
        }

        String token = jwtService.generateToken(
                u.getUsername(),
                Map.of("rol", u.getRol().name())
        );

        return ResponseEntity.ok(new AuthResponse(token));
    }
}