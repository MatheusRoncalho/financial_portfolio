package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.config.JwtTokenProvider;
import com.roncalho.financial_portfolio.dto.in.LoginRequestDTO;
import com.roncalho.financial_portfolio.dto.in.RegisterRequestDTO;
import com.roncalho.financial_portfolio.dto.out.LoginResponseDTO;
import com.roncalho.financial_portfolio.dto.out.RegisterResponseDTO;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Operações de autenticação de usuários")
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
    @Operation(summary = "Registrar Usuários", description = "Registro de novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<RegisterResponseDTO> registrarUsuario(@Valid @RequestBody RegisterRequestDTO dto) {
        RegisterResponseDTO response = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

     @PostMapping("/login")
     @Operation(summary = "Login de Usuários", description = "Autenticação de usuário e geração de JWT token")
     @ApiResponse(responseCode = "200", description = "Autenticado com sucesso")
     @ApiResponse(responseCode = "400", description = "Dados inválidos")
     @ApiResponse(responseCode = "401", description = "Email ou senha incorretos")
     @ApiResponse(responseCode = "500", description = "Erro de servidor")
     public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
         Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
         );

         SecurityContextHolder.getContext().setAuthentication(authentication);

         Usuario usuario = usuarioService.buscarPorEmail(dto.email())
                 .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

         String token = jwtTokenProvider.gerarToken(authentication, usuario.getId());

         LoginResponseDTO response = new LoginResponseDTO(
                 token,
                 usuario.getId(),
                 usuario.getUsername(),
                 usuario.getEmail(),
                 usuario.getCriadoEm()
         );

         return ResponseEntity.ok(response);
     }

    @PostMapping("/logout")
    @Operation(summary = "Logout de Usuários", description = "Realiza o logout do usuário invalidando a sessão")
    @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Obter Dados do Usuário Autenticado", description = "Retorna os dados do usuário atualmente logado")
    @ApiResponse(responseCode = "200", description = "Dados obtidos com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<RegisterResponseDTO> obterUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        RegisterResponseDTO response = new RegisterResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getAtivo(),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm()
        );

        return ResponseEntity.ok(response);
    }
}

