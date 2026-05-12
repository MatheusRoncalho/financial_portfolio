package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.LoginRequestDTO;
import com.roncalho.financial_portfolio.dto.in.RegisterRequestDTO;
import com.roncalho.financial_portfolio.dto.out.LoginResponseDTO;
import com.roncalho.financial_portfolio.dto.out.RegisterResponseDTO;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UsuarioService usuarioService,
                         AuthenticationManager authenticationManager,
                         JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registrar(@Valid @RequestBody RegisterRequestDTO dto) {
        RegisterResponseDTO response = usuarioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.gerarToken(authentication);

        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<RegisterResponseDTO> obterUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        RegisterResponseDTO response = new RegisterResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}

