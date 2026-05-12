package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.RegisterRequestDTO;
import com.roncalho.financial_portfolio.dto.out.RegisterResponseDTO;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDTO registrar(RegisterRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.username())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.password()))
                .ativo(true)
                .build();

        Usuario saved = usuarioRepository.save(usuario);

        return new RegisterResponseDTO(saved.getId(), saved.getNome(), saved.getEmail());
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarOuLancarException(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = buscarOuLancarException(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}

